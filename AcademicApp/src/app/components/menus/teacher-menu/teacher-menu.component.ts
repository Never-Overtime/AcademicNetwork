import { Component, Input, OnInit } from '@angular/core';
import { teacherMenuData, chiefMenuData } from './teacherMenuData';
import { SIDEMENU_WIDTH, SIDEMENU_PADDING } from 'src/app/constants/sizes';
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-teacher-menu',
  templateUrl: './teacher-menu.component.html',
  styleUrls: ['./teacher-menu.component.css']
})
export class TeacherMenuComponent implements OnInit {
  teacherData = teacherMenuData;
  chiefData = chiefMenuData;
  sidemenuWidth = SIDEMENU_WIDTH;
  sidemenuPadding = SIDEMENU_PADDING;

  @Input() name : string = 'Name Surnameeeeeeeeeeeeeeeee';

  constructor(private cookieService: CookieService) { }

  ngOnInit(): void {
  }

  logout(){
    console.log("button pressed");
    this.cookieService.delete('username');
  }

}
