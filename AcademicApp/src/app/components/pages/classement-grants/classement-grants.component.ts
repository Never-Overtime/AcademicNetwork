import { Component, OnInit } from '@angular/core';
import { LOGO_WIDTH, PAGE_PADDING, CONTENT_PADDING } from 'src/app/constants/sizes';

@Component({
  selector: 'app-classement-grants',
  templateUrl: './classement-grants.component.html',
  styleUrls: ['./classement-grants.component.css']
})
export class ClassementGrantsComponent implements OnInit {
  logoWidth = LOGO_WIDTH
  pagePadding = PAGE_PADDING
  contentPadding = CONTENT_PADDING

  tableHeaders = ['Username', 'Final grade']

  studentsData = [
    {
      'Username': 'username_1',
      'Final grade': 1
    },
    {
      'Username': 'username_2',
      'Final grade': 2
    },
    {
      'Username': 'username_3',
      'Final grade': 3
    },
    {
      'Username': 'username_4',
      'Final grade': 4
    },
    {
      'Username': 'username_5',
      'Final grade': 5
    }
  ]

  gradePlaceholder = 'Input value';
  moneyPlaceholder = 'Input value';

  constructor() { }

  ngOnInit(): void {
  }

}
