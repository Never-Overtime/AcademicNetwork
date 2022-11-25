import { Component, OnInit } from '@angular/core';
import { LOGO_WIDTH, PAGE_PADDING, CONTENT_PADDING } from 'src/app/constants/sizes';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  logoWidth = LOGO_WIDTH
  pagePadding = PAGE_PADDING
  contentPadding = CONTENT_PADDING

  constructor() { }

  ngOnInit(): void {
  }

}
