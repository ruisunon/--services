import { Component } from '@angular/core';
import { TeacherService } from 'src/app/services/teacher-service/teacher.service';

@Component({
  selector: 'app-all-teachers',
  templateUrl: './all-teachers.component.html',
  styleUrls: ['./all-teachers.component.css']
})
export class AllTeachersComponent {

  teachers: any;

  constructor(private teacherService: TeacherService) { }

  ngOnInit() {
    this.getAllteachers();
  }

  getAllteachers() {
    this.teacherService.getAllTeachers().subscribe((res) => {
      console.log(res);
      this.teachers = res.data
    })
  }

  deleteTeacher(teacherId: any) {
    this.teacherService.deleteTeacher(teacherId).subscribe((res) => {
      console.log(res);
      this.getAllteachers();
    })
  }



}
