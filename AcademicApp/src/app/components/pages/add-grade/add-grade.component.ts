import {Component, OnInit, ViewChild} from '@angular/core';
import {LOGO_WIDTH, PAGE_PADDING, CONTENT_PADDING} from 'src/app/constants/sizes';
import {ApisService} from "../../../apis/apis.service";
import {CookieService} from "ngx-cookie-service";
import {MatDialog} from "@angular/material/dialog";
import {Course} from "../../../entities/course";
import {TableComponent} from "../../table/table.component";
import {DropdownComponent} from "../../dropdown/dropdown.component";
import {BLUE_TEXT} from "../../../constants/colors";

@Component({
  selector: 'app-add-grade',
  templateUrl: './add-grade.component.html',
  styleUrls: ['./add-grade.component.css']
})
export class AddGradeComponent implements OnInit {
  logoWidth = LOGO_WIDTH
  pagePadding = PAGE_PADDING
  contentPadding = CONTENT_PADDING
  options: Course[] = [];
  nameOfPropertyToShow: string = "name";

  @ViewChild('table') table!: TableComponent;
  @ViewChild('coursesDd') coursesDropdown!: DropdownComponent; // reference to dropdown

  tableHeaders = ["Username", "Name", "Group", 'Grade'];
  tableData: any[] = [];
  blueText = BLUE_TEXT;

  constructor(private apiService: ApisService, private cookieService: CookieService, public dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.apiService.getCoursesOfTeacher().subscribe((courses) => {
      this.options = courses;
    })
  }

  onOptionChanged() {
    this.table.changeRowsData([])
    let obj: Course = this.coursesDropdown.getSelectedObject()[0];
    this.apiService.getStudentGradesForCourse(obj).subscribe((studentGrades) => {

      for (let studentGrade of studentGrades) {
        let o = {
          "Username": studentGrade.username,
          "Name": studentGrade.name,
          "Group": studentGrade.group,
          "Grade": studentGrade.gradeValue,
          "studentGrade": studentGrade
        };

        this.table.addRowData(o)
      }
    })
  }

}
