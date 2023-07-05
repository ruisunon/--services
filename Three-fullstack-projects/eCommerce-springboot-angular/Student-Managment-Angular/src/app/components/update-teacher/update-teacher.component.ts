import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { TeacherService } from 'src/app/services/teacher-service/teacher.service';

@Component({
  selector: 'app-update-teacher',
  templateUrl: './update-teacher.component.html',
  styleUrls: ['./update-teacher.component.css']
})
export class UpdateTeacherComponent {

  teacherId: any = this.activatedRoute.snapshot.params["teacherId"];
  validateForm: FormGroup;

  constructor(private teacherSerice: TeacherService,
    private activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private router: Router) { }

  ngOnInit() {
    this.validateForm = this.fb.group({
      name: [null, [Validators.required]],
      department: [null, [Validators.required]],
    })
    this.getTeacherById();
  }

  getTeacherById() {
    this.teacherSerice.getTeacherById(this.teacherId).subscribe((res) => {
      console.log(res);
      this.validateForm.patchValue(res.data.teacherDto);
    })
  }

  updateTeacher() {
    this.teacherSerice.updateTeacher(this.teacherId, this.validateForm.value).subscribe((res) => {
      console.log(res);
      this.router.navigateByUrl("/teachers")
    })
  }

}
