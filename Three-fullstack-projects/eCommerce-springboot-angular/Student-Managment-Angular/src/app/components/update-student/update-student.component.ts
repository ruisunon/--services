import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { StudentService } from 'src/app/services/student-service/student.service';

@Component({
  selector: 'app-update-student',
  templateUrl: './update-student.component.html',
  styleUrls: ['./update-student.component.css']
})
export class UpdateStudentComponent {

  studentId: any = this.activatedRoute.snapshot.params['studentId'];
  validateForm: FormGroup;

  constructor(private studentService: StudentService,
    private activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private router: Router) { }

  ngOnInit() {
    this.validateForm = this.fb.group({
      name: [null, [Validators.required]],
      fatherName: [null, [Validators.required]],
      motherName: [null, [Validators.required]],
    })
    this.getStudentById();
  }

  getStudentById() {
    this.studentService.getStudentById(this.studentId).subscribe((res) => {
      console.log(res);
      this.validateForm.patchValue(res.data.studentDto)
    })
  }

  updateStudent() {
    this.studentService.updateStudent(this.studentId, this.validateForm.value).subscribe((res) => {
      console.log(res);
      this.router.navigateByUrl("/dashboard");
    })
  }

}
