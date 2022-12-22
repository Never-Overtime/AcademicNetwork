import {CourseStatistics} from "./courseStatistics";

export class TeacherStatistics{
  constructor(
    public username: string,
    public fullname: string,
    public courseStatisticsList: CourseStatistics[]
  ) {}
}
