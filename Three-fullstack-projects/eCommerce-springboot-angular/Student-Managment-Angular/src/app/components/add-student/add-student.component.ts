import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StudentService } from 'src/app/services/student-service/student.service';

@Component({
  selector: 'app-add-student',
  templateUrl: './add-student.component.html',
  styleUrls: ['./add-student.component.css']
})
export class AddStudentComponent {

  validateForm: FormGroup;

  constructor(private studentService: StudentService,
    private fb: FormBuilder) { }

  ngOnInit() {
    this.validateForm = this.fb.group({
      name: [null, [Validators.required]],
      fatherName: [null, [Validators.required]],
      motherName: [null, [Validators.required]],
    })
  }

  addStudent() {
    this.studentService.addStudent(this.validateForm.value).subscribe((res) => {
      console.log(res);
    })
  }


}
