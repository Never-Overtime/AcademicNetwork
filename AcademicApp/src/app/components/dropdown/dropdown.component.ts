import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'dropdown',
  templateUrl: './dropdown.component.html',
  styleUrls: ['./dropdown.component.css']
})
export class DropdownComponent implements OnInit {
  selectedOption: any;


  @Input() options: any = [
    {
      text: 'Mere',
      otherStuff: 'vvdvvdf',
      value: 1
    },
    {
      text: 'Pere',
      otherStuff: 'aaaaaa',
      value: 2
    },
    {
      text: 'Banane',
      otherStuff: 'bbbbbb',
      value: 3
    },
  ];
  @Input() labelText: string = 'Choose option';
  @Input() placeholderText: string = 'All';
  @Input() shownProperty: any = 'text';
  @Input() idProperty: any = 'value';
  @Input() hasAll: boolean = true;
  constructor() { }

  ngOnInit(): void {
  }

   selected(){


   }

  getSelectedValue(){
    return this.selectedOption;
  }

  getSelectedObject(){
    const id = this.getSelectedValue();
    let ceva = this.options.filter((my_option:any) => {
      return my_option[this.idProperty] == id;
    });
    return ceva;
  }

  setOptions(newOptions: any){
    this.options = newOptions
  }


}
