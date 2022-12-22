import {Component, OnInit, ViewChild} from '@angular/core';
import {FacultyAndYearData} from "../../../entities/facultyAndYearData";
import {BLUE_TEXT} from "../../../constants/colors";
import {CONTENT_PADDING, LOGO_WIDTH, PAGE_PADDING} from "../../../constants/sizes";
import {DropdownComponent} from "../../dropdown/dropdown.component";
import {TableComponent} from "../../table/table.component";
import {Course} from "../../../entities/course";
import {CourseGradeData} from "../../../entities/courseGradeData";
import {TABLE_TEST_CURRICULUM_DATA, TABLE_TEST_CURRICULUM_DATA_AFTER} from "../../../testing-dashboard/testingData";

@Component({
  selector: 'app-curriculum-grades-page',
  templateUrl: './curriculum-grades-page.component.html',
  styleUrls: ['./curriculum-grades-page.component.css']
})
export class CurriculumGradesPageComponent implements OnInit {
  logoWidth = LOGO_WIDTH
  pagePadding = PAGE_PADDING
  contentPadding = CONTENT_PADDING
  blueText = BLUE_TEXT
  options: FacultyAndYearData[] = [];
  nameOfPropertyToShow: string = "name";

  @ViewChild('facultyDd') facultyDropdown!: DropdownComponent; // reference to dropdown
  @ViewChild('table') table!: TableComponent; // reference to dropdown

  headers = ['course', 'grade'];
  tableData = TABLE_TEST_CURRICULUM_DATA;
  tableDataAfter = TABLE_TEST_CURRICULUM_DATA_AFTER;

  constructor(private apisService: ApisService) {

  }

  ngOnInit(): void {
    facultyAndYear: Array<FacultyAndYearData>();

    // getting the faculties and years that the student is enrolled in
    this.apisService.getFacultiesAndYearsForStudent().subscribe((result) => {
      let array : any = [];
      // creating the array for the dropdown element
      result.forEach((value, index) =>{
        let obj = {"id":index, "name": value.name + " - Year " + value.year, "actualName": value.name, "actualYear": value.year};
        array.push(obj);
      })
      // setting the options for the dropdown
      this.options = array;
      // putting the data into the table for the first faculty that the guy/girl/non binary/etc is in
      if(result.length > 0){ // checking if the guy is enrolled at least in a faculty in the first place
        this.apisService.getGrades(result[0]).subscribe((result) =>{
          this.table.changeRowsData(result); // this is how you change it.
          this.facultyDropdown.setOptions(this.options); // updating the options for the dropdown list
        });

      }

    });
  }

  onOptionChanged() {
    let obj = this.facultyDropdown.getSelectedObject()[0];
    // creating the data to send for the api
    let model : FacultyAndYearData = new FacultyAndYearData(obj["actualName"], obj["actualYear"]);

    // getting the faculties for student
    let facultiesAttended: FacultyAndYearData[] = [];

    this.apisService.getFacultiesAndYearsForStudent().subscribe((fac :FacultyAndYearData[]) =>{
      facultiesAttended = fac;

      // finding out which group (first or second) from student is the faculty for
      let indexOfGroup : number = 0;
      let index : number = 0;

      for(var value of facultiesAttended){
        index = index + 1;
        if(value.name == model.name){
          indexOfGroup = index;
        }

      }

      //getting the obligatory & facultative courses that the faculty and year selected has
      let coursesMust : Course[] = []

      if(indexOfGroup == 1){
        this.apisService.getCoursesForStudentFirstGroup().subscribe((result)=>{
          coursesMust = result;
          this.onOptionChangedNext(coursesMust);
        })
      }
      else{
        if(indexOfGroup == 2){
          this.apisService.getCoursesForStudentSecondGroup().subscribe((result)=>{
            coursesMust = result;
            this.onOptionChangedNext(coursesMust);
          })
        }
      }
    });
  }

  onOptionChangedNext(coursesMust : Course[]){
    let obj = this.facultyDropdown.getSelectedObject()[0];
    // creating the data to send for the api
    let model : FacultyAndYearData = new FacultyAndYearData(obj["actualName"], obj["actualYear"]);

    // getting all the courses(bot faculties if he is enrolled in 2) with the optional
    let allCoursesWithOptionalForStudent : Course[] = [];

    this.apisService.getAllCoursesForWhichEnrolled().subscribe((result: Course[]) =>{
      allCoursesWithOptionalForStudent = result;
      // creating a CourseGradeData for the tabel where i select only the courses i need
      let coursesAndGradesList : CourseGradeData[] = [];

      for(var course of allCoursesWithOptionalForStudent){

        let exists : boolean = false;

        for(var courseMust of coursesMust){
          if(course.cid == courseMust.cid){
            exists = true;
          }
        }

        if(exists){
          coursesAndGradesList.push(new CourseGradeData(course.name, -1))
        }
      }
      // getting the courses and grades that the student is enrolled in
      this.apisService.getGrades(model).subscribe((result) =>{

        for(var gradedCourse of result){
          for(var soonToBeGradedCourseMaybe of coursesAndGradesList){
            if(gradedCourse.course == soonToBeGradedCourseMaybe.course){
              soonToBeGradedCourseMaybe.grade = gradedCourse.grade
            }
          }
        }
        this.table.changeRowsData(coursesAndGradesList)
      });
       })
  }
}
