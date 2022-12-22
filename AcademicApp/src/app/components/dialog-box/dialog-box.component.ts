import { Component, Inject, OnInit, Optional } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';


@Component({
  selector: 'app-dialog-box',
  templateUrl: './dialog-box.component.html',
  styleUrls: ['./dialog-box.component.css']
})
export class DialogBoxComponent implements OnInit{
  action: string;
  local_data: any;

  // for adding new course modal
  facultyOptionsDd = [{'value':1,'viewValue':'Facultate1'}, {'value':2,'viewValue':'Facultate2'}];
  yearOptionsDd = [1,2,3];
  semesterOptionsDd = [1,2,3,4,5,6];
  creditsOptionsDd = [1,2,3,4,5,6,7,8];

  dropdownData = {
    'facultyOptions': this.facultyOptionsDd,
    'yearOptions': this.yearOptionsDd,
    'semesterOptions': this.semesterOptionsDd,
    'creditsOptions': this.semesterOptionsDd,
  }

  ngOnInit(): void {
    this.facultyOptionsDd = this.getFaculties();
  }

  constructor(
    public dialogRef: MatDialogRef<DialogBoxComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any,) {
        console.log(data);
        this.local_data = {...data};
        this.action = this.local_data.action;
  }

  getFaculties(): any{
    /**
     * @TO_DO
     */

  }

  getYears(data:any){
    /**
     * @TO_DO
     */
    let fid = data.value; // faculty id
    

    // here you get the years 
    let years = [1,2,3,4,5];
    this.dropdownData['yearOptions'] = years;
  }

  doAction(){
    this.dialogRef.close({event:this.action,data:this.local_data});
  }

  closeDialog(){
    this.dialogRef.close({event:'Cancel'});
  }
}
