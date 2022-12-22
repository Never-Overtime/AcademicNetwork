import { Component, OnInit, ViewChild } from '@angular/core';
import { LOGO_WIDTH, PAGE_PADDING, CONTENT_PADDING } from 'src/app/constants/sizes';
import { TableComponent } from '../../table/table.component';

@Component({
  selector: 'app-approve-optionals',
  templateUrl: './approve-optionals.component.html',
  styleUrls: ['./approve-optionals.component.css']
})

export class ApproveOptionalsComponent implements OnInit {
  logoWidth = LOGO_WIDTH
  pagePadding = PAGE_PADDING
  contentPadding = CONTENT_PADDING
 
  @ViewChild('table') table!: TableComponent;

  tableHeaders = ['Optional course name', 'Max students'];

  coursesData = [
    {
      'cid': 1,
      'Optional course name': 'Optional_1',
      'Max students': 1
    },
    {
      'cid': 2,
      'Optional course name': 'Optional_2',
      'Max students': 2
    },
    {
      'cid': 3,
      'Optional course name': 'Optional_3',
      'Max students': 3
    },
    {
      'cid': 4,
      'Optional course name': 'Optional_4',
      'Max students': 4
    },
    {
      'cid': 5,
      'Optional course name': 'Optional_5',
      'Max students': 5
    }
  ]

  approveOptionalsButton() {
    /**
     * @TO_DO
     */
    console.log(this.table.getSelectedRowsData());
  }

  constructor() { }

  ngOnInit(): void {
  }

}
