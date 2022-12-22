import { Component, Input, OnInit } from '@angular/core';
import { adminMenuData } from './adminMenuData';
import { SIDEMENU_WIDTH, SIDEMENU_PADDING } from 'src/app/constants/sizes';
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-admin-menu',
  templateUrl: './admin-menu.component.html',
  styleUrls: ['./admin-menu.component.css']
})
export class AdminMenuComponent implements OnInit {
  navData = adminMenuData;
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
