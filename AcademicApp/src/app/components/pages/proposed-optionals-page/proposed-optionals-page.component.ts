import { Component, OnInit } from '@angular/core';
import {Course} from "../../../entities/course";
import {CONTENT_PADDING, LOGO_WIDTH, PAGE_PADDING} from "../../../constants/sizes";
import {ApisService} from "../../../apis/apis.service";
import {CdkDragDrop, moveItemInArray, transferArrayItem} from "@angular/cdk/drag-drop";

@Component({
  selector: 'app-proposed-optionals-page',
  templateUrl: './proposed-optionals-page.component.html',
  styleUrls: ['./proposed-optionals-page.component.css']
})
export class ProposedOptionalsPageComponent implements OnInit {

  logoWidth = LOGO_WIDTH
  pagePadding = PAGE_PADDING
  contentPadding = CONTENT_PADDING

  constructor(private apisService: ApisService) {
  }

  ngOnInit(): void {
    this.getOptionals();
    this.checkIfBtnGottaBeDisabled();
  }

  optionals: Course[] = []
  chosenOptionals: Course[] = []

  entities: any = [];
  disabledButton: boolean = false;
  disabled: boolean = false;

  getOptionals() {

    this.apisService.getAllOptionals()
      .subscribe((c: Course[]) => {
        c.forEach((value, index) => {
          this.optionals.push(value)
        })
      })
  }

  drop(event: CdkDragDrop<any>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
      this.optionals = [...this.optionals];
    } else {
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex
      );
      this.chosenOptionals = [...this.chosenOptionals];
    }
  }

  eventPredicate() {
    return this.chosenOptionals && this.chosenOptionals.length < 5;
  }

  eventFirstPredicate() {
    return this.optionals && this.optionals.length < 1000;
  }

  chosenOptionalsPredicate = (): boolean => {
    return this.eventPredicate();
  }

  optionalsPredicate = (): boolean => {
    return this.eventFirstPredicate();
  }



  sendOptionals() {
    this.apisService.postOptionalsPreferences(this.chosenOptionals)
      .subscribe((m: any) => {
        if (m == null)
          alert("You cannot add more optionals!")
        else{
          alert("Your optionals have been set!");
        }
      })
  }

  checkIfBtnGottaBeDisabled() {
    this.apisService.checkIfAssignEnabledForEveryone()
      .subscribe((n) => {
        if (n == 0){
          this.disabled = true;
          alert("You have been assigned to an optional! You cannot send anymore.")
        }
      })
  }

}
