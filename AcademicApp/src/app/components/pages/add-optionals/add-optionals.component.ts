import {Component, OnInit, ViewChild} from '@angular/core';
import {DialogBoxComponent} from "../../dialog-box/dialog-box.component";
import {TableComponent} from "../../table/table.component";
import {MatDialog} from "@angular/material/dialog";
import {CONTENT_PADDING, LOGO_WIDTH, PAGE_PADDING} from "../../../constants/sizes";
import {BLUE_TEXT} from "../../../constants/colors";
import {ButtonComponent} from "../../basic-components/button/button.component";

@Component({
  selector: 'app-add-optionals',
  templateUrl: './add-optionals.component.html',
  styleUrls: ['./add-optionals.component.css']
})
export class AddOptionalsComponent implements OnInit {
  logoWidth = LOGO_WIDTH
  pagePadding = PAGE_PADDING
  contentPadding = CONTENT_PADDING
  blueText = BLUE_TEXT

  // din curs intreg eu le afisez numa pe alea de jos.
  // eu primesc un curs intreg dar folosesc numai: numele cursului, facultatea, semestrul, anul, nr de credite
  headers = ['name', 'facultyName', 'semester', 'year', 'credits'];
  tableData = [];

  constructor(public dialog: MatDialog) {}

  @ViewChild('addOptionalBtn') addBtn!: ButtonComponent;
  @ViewChild(TableComponent,{static:true}) table!: TableComponent; // reference to table


  ngOnInit(): void {
    this.tableData = this.getOptionals();
  }

  sendOptionals(){
    /** @TO_DO : send to backendnumele facultatii, numele cursuluianul in care se face, teacherul, semestrul, priority e mereu 2, nr credits */

    let data = this.table.getAllRowsData();
    if(data.length == 0){
      alert("You did not create any optionals");
    }
    console.log(this.table.getAllRowsData());

  }

  getOptionals() {
    /** @TO_DO : send to backendnumele facultatii, numele cursuluianul in care se face, teacherul, semestrul, priority e mereu 2, nr credits */
    return [];
  }

  openDialog(action: string, obj:any) {
    obj.action = action;
    if(action == 'Add') {
      let nrOptionals = this.table.getAllRowsData().length;
      if(nrOptionals == 2) {
        alert("You can add at most 2 optionals...edit or delete existing ones");
        return;
      }
    }

    const dialogRef = this.dialog.open(DialogBoxComponent, {
      width: '650px',
      data:obj,
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result.event == 'Add'){
        this.addRowData(result.data);
        console.log(this.tableData);
      }
    });
  }

  /** if you press on add button */
  addRowData(row_obj:any){
    if(this.validateNewOptional(row_obj)) {
      delete row_obj["action"];
      row_obj["cid"] = this.table.getAllRowsData().length + 1; // in case you also need id = here for experimental purposes
      row_obj["max_students"] = 20; // default value for max students
      row_obj["priority"] = 2;
      row_obj["teacher"] = 'Bread Sheran'; // here you need to get full name..
      row_obj = {...row_obj};

      this.table.addRowData(row_obj);
    }
  }

  validateNewOptional(optional:any): boolean{
    if(!optional.hasOwnProperty("name")){
      alert("Error! Please input a name...");
      return false;
    }
    if(!optional.hasOwnProperty("fid")){
      alert("Error! Please input a faculty...");
      return false;
    }
    if (!optional.hasOwnProperty("year")){
      alert("Error! Please input a year...");
      return false;
    }
    if (!optional.hasOwnProperty("semester")){
      alert("Error! Please input a semester...");
      return false;
    }
    if (!optional.hasOwnProperty("credits")){
      alert("Error! Please input nr credits...")
      return false;
    }

    return true;
  }
}
