import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserStorageService } from '../storage/user-storage.service';
import { Observable } from 'rxjs';

const BASIC_URL = "http://localhost:8080/";

@Injectable({
  providedIn: 'root'
})
export class TeacherService {

  constructor(private http: HttpClient) { }

  addTeacher(teacherDto: any) {
    return this.http.post(BASIC_URL + `api/teacher/${UserStorageService.getUserId()}`, teacherDto, {
      headers: this.createdAuthorizationHeader(),
    });
  }

  getAllTeachers(): Observable<any> {
    return this.http.get(BASIC_URL + `api/teachers`, {
      headers: this.createdAuthorizationHeader(),
    });
  }

  deleteTeacher(teacherId: any): Observable<any> {
    return this.http.delete(BASIC_URL + `api/teacher/${teacherId}`, {
      headers: this.createdAuthorizationHeader(),
    });
  }

  getTeacherById(teacherId: any): Observable<any> {
    return this.http.get(BASIC_URL + `api/getTeacherById/${teacherId}`, {
      headers: this.createdAuthorizationHeader(),
    });
  }

  updateTeacher(teacherId: any, teacherDto: any) {
    return this.http.put(BASIC_URL + `api/teacher/${teacherId}`, teacherDto, {
      headers: this.createdAuthorizationHeader(),
    });
  }

  createdAuthorizationHeader(): HttpHeaders {
    let authHeader: HttpHeaders = new HttpHeaders();
    return authHeader.set(
      "Authorization",
      "Bearer " + UserStorageService.getToken()
    )
  }


}
