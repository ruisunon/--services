import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { TeacherService } from 'src/app/services/teacher-service/teacher.service';

@Component({
  selector: 'app-add-teacher',
  templateUrl: './add-teacher.component.html',
  styleUrls: ['./add-teacher.component.css']
})
export class AddTeacherComponent {

  validateForm: FormGroup;

  constructor(private teacherService: TeacherService,
    private fb: FormBuilder) { }

  ngOnInit() {
    this.validateForm = this.fb.group({
      name: [null, [Validators.required]],
      department: [null, [Validators.required]],
    })
  }

  addTeacher() {
    this.teacherService.addTeacher(this.validateForm.value).subscribe((res) => {
      console.log(res);
    })
  }


}


