import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { LOGO_WIDTH, PAGE_PADDING, CONTENT_PADDING } from 'src/app/constants/sizes';
import * as jspdf from 'jspdf';
import html2canvas from 'html2canvas';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import { FullName } from 'src/app/entities/fullName';
import { FacultyAndYearData } from 'src/app/entities/facultyAndYearData';
import { ApisService } from 'src/app/apis/apis.service';
import { DropdownComponent } from '../../dropdown/dropdown.component';
import { Course } from 'src/app/entities/course';
import { DragAndDropComponent } from '../../drag-and-drop/drag-and-drop.component';


@Component({
  selector: 'app-documents-page',
  templateUrl: './documents-page.component.html',
  styleUrls: ['./documents-page.component.css']
})
export class DocumentsPageComponent implements OnInit {
  logoWidth = LOGO_WIDTH
  pagePadding = PAGE_PADDING
  contentPadding = CONTENT_PADDING
  coursesList : Course[] = [];
  @ViewChild('facultyDd') facultyDropdown!: DropdownComponent; // reference to dropdown
  @ViewChild('fileTable') fileTable!: DragAndDropComponent; // reference to dropdown

  facultiesOptions: any = [];
  options: FacultyAndYearData[] = [];
  name: string = '';
  faculty: string = '';
  yearOfStudy: string = '';
  courses = [

  ]

idFPropery: string = "name";
nameOfFPropertyToShow: string = "name";

headers=['cid', 'name', 'semester', 'priority', 'credits'];
exportAsPDF(divId: any) {
  const data = document.createElement('myContractDiv');
  document.body.appendChild(data);

  let dataHtml = `
    <style>
    table, th, td {
    border:1px solid black;
    }
    </style>

    <h1>STUDIES KONTRACT</h1>
    <br>
    <p>Name of student: ${this.name}</p>
    <p>Faculty: ${this.faculty}</p>
    <p>Year of study: ${this.yearOfStudy}</p>
    <table>
      <tr>
        <th>cid</th>
        <th>name</th>
        <th>semester</th>
        <th>priority</th>
        <th>credits</th>
      </tr>`

      for (let i = 0; i < this.coursesList.length; i++) {

        dataHtml += `
          <tr>
            <td>${this.coursesList[i].cid}</td>
            <td>${this.coursesList[i].name}</td>
            <td>${this.coursesList[i].semester}</td>
            <td>${this.coursesList[i].priority}</td>
            <td>${this.coursesList[i].credits}</td>
          </tr>
        `
      }
      dataHtml += `
      </table>

    <br><br><br><br>
    <p>Student signature:<br>_______________</p>
      `
    data.innerHTML = dataHtml;
    this.coursesList = [];

    html2canvas(data).then(canvas => {
    const contentDataURL = canvas.toDataURL('image/png')  // 'image/jpeg' for lower quality output.
    let pdf = new jspdf.jsPDF('p', 'cm', 'a4'); //Generates PDF in portrait mode

    let pageHeight = 29.75;
    let imgHeight = 8 + (this.courses.length * 2.5);
    let heightLeft = imgHeight;
    let position = 0;

    pdf.addImage(contentDataURL, 'jpg', 2, position, 30, imgHeight);
    heightLeft -= pageHeight;
    while(heightLeft >= 0) {
      position = heightLeft - imgHeight;
      pdf.addPage();
      pdf.addImage(contentDataURL, 'jpg', 2, position, 30, imgHeight);
      heightLeft -= pageHeight;
    }
    const e = document.querySelector("myContractDiv");
    if (e) {
      const pe = e.parentElement;
      if (pe) {
        pe.removeChild(e);
      }
    }
    pdf.save('Filename.pdf');
    });
  }

  constructor(private cookieService: CookieService, private http: HttpClient, private apisService: ApisService) { }

  ngOnInit(): void {
    this.getFullName();
    this.apisService.getFacultiesAndYearsForStudent().subscribe((result) => {
      let array : any = [];
      result.forEach((value, index) =>{
        let obj = {"id":index, "name": value.name + " - Year " + value.year, "actualName": value.name, "actualYear": value.year};
        array.push(obj);
      })
      this.options = array;
      if(result.length > 0){
          this.facultyDropdown.setOptions(this.options);

      }

    });

  }

  getFullName(){
    this.http.get<FullName>("http://localhost:8080/userdata/" + this.cookieService.get("username") + "/" +
      this.cookieService.get("username"))
      .subscribe((response:FullName) => {
        this.name = response.fullname;
      });
  }


  onOptionChanged() {
    let obj = this.facultyDropdown.getSelectedObject()[0];
    let model : FacultyAndYearData = new FacultyAndYearData(obj["actualName"], obj["actualYear"]);

    this.faculty = model.name;
    this.yearOfStudy =`${model.year}`;

    let facultiesAttended: FacultyAndYearData[] = [];

    this.apisService.getFacultiesAndYearsForStudent().subscribe((fac :FacultyAndYearData[]) =>{
      facultiesAttended = fac;

    let indexOfGroup : number = 0;
    let index : number = 0;

    for(var value of facultiesAttended){
      index = index + 1;
      if(value.name == model.name){
        indexOfGroup = index;
      }

    }

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
    let model : FacultyAndYearData = new FacultyAndYearData(obj["actualName"], obj["actualYear"]);

    let allCoursesWithOptionalForStudent : Course[] = [];


    this.apisService.getAllCoursesForWhichEnrolled().subscribe((result: Course[]) =>{
      allCoursesWithOptionalForStudent = result;
      for(var course of allCoursesWithOptionalForStudent){

        let exists : boolean = false;

        for(var courseMust of coursesMust){
          if(course.cid == courseMust.cid){
            exists = true;
          }
        }

        if(exists){
            this.coursesList.push(new Course(course.cid, course.name, course.fid, course.year, course.teacher, course.semester, course.maxstudents, course.priority, course.credits))
        }
      }
      })
  }

  onUpdateClicked(){
    this.fileTable.deleteAllFiles();
    alert("Uploaded successfully!");
  }
}
