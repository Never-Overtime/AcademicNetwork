import { Component, Input, OnInit } from '@angular/core';
import { studentMenuData } from './studentMenuData';
import { SIDEMENU_WIDTH, SIDEMENU_PADDING } from 'src/app/constants/sizes';

@Component({
  selector: 'app-student-menu',
  templateUrl: './student-menu.component.html',
  styleUrls: ['./student-menu.component.css']
})

export class StudentMenuComponent implements OnInit {
  navData = studentMenuData;
  sidemenuWidth = SIDEMENU_WIDTH;
  sidemenuPadding = SIDEMENU_PADDING;

  @Input() name : string = 'Name Surnameeeeeeeeeeeeeeeee';

  constructor() { }

  ngOnInit(): void {
  }

}
