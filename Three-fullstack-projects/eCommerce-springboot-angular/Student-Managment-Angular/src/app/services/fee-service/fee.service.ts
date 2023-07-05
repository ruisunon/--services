import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserStorageService } from '../storage/user-storage.service';
import { Observable } from 'rxjs';


const BASIC_URL = "http://localhost:8080/";

@Injectable({
  providedIn: 'root'
})
export class FeeService {

  constructor(private http: HttpClient) { }

  payFee(studentId: any, feeDto: any): Observable<any> {
    return this.http.post(BASIC_URL + `api/fee/${studentId}`, feeDto, {
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
