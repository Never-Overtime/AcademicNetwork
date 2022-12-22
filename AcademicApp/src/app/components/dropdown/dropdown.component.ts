import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-dropdown',
  templateUrl: './dropdown.component.html',
  styleUrls: ['./dropdown.component.css']
})
export class DropdownComponent implements OnInit {
  @Input() options: any;
  @Input() labelText: string;
  @Input() placeholderText: string;
  @Input() shownProperty: any;
  @Input() idProperty: any;

  constructor() { }

  ngOnInit(): void {
  }

}
