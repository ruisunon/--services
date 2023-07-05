import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserStorageService } from '../storage/user-storage.service';
import { Observable } from 'rxjs';


const BASIC_URL = "http://localhost:8080/";

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  constructor(private http: HttpClient) { }

  addStudent(studentDto: any): Observable<any> {
    console.log(studentDto);
    console.log(this.createdAuthorizationHeader());
    return this.http.post(BASIC_URL + `api/student/${UserStorageService.getUserId()}`, studentDto, {
      headers: this.createdAuthorizationHeader(),
    });
  }

  getAllStudents(): Observable<any> {
    return this.http.get(BASIC_URL + "api/students", {
      headers: this.createdAuthorizationHeader(),
    })
  }

  getStudentById(studentId: any): Observable<any> {
    return this.http.get(BASIC_URL + `api/studentById/${studentId}`, {
      headers: this.createdAuthorizationHeader(),
    })
  }

  updateStudent(studentId: any, studentDto: any): Observable<any> {
    return this.http.put(BASIC_URL + `api/student/${studentId}`, studentDto, {
      headers: this.createdAuthorizationHeader(),
    })
  }

  deleteStudent(studentId: any): Observable<any> {
    return this.http.delete(BASIC_URL + `api/student/${studentId}`, {
      headers: this.createdAuthorizationHeader(),
    })
  }

  searchStudentByName(title: any): Observable<any> {
    return this.http.get(BASIC_URL + `api/search/${title}`, {
      headers: this.createdAuthorizationHeader(),
    })
  }

  createdAuthorizationHeader(): HttpHeaders {
    let authHeader: HttpHeaders = new HttpHeaders();
    return authHeader.set(
      "Authorization",
      "Bearer " + UserStorageService.getToken()
    )
  }

}
