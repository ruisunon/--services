import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { StudentService } from 'src/app/services/student-service/student.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

  students: any;
  validateForm: FormGroup;

  constructor(private studentService: StudentService,
    private fb: FormBuilder) { }

  ngOnInit() {
    this.validateForm = this.fb.group({
      title: [null]
    })
    this.getAllStudents();
  }

  getAllStudents() {
    this.studentService.getAllStudents().subscribe((res) => {
      console.log(res);
      this.students = res.data;
    })
  }

  deleteStudent(studentId: any) {
    this.studentService.deleteStudent(studentId).subscribe((res) => {
      console.log(res);
      this.getAllStudents();
    })
  }

  searchStudentByName() {
    this.studentService.searchStudentByName(this.validateForm.get(['title'])!.value).subscribe((res) => {
      console.log(res);
      this.students = res.data;
    })
  }

}
