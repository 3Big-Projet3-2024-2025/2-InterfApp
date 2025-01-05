import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AnswerService {

  private baseUrl = 'http://localhost:8080/api/answers'; // base url for api backend

  constructor(private http: HttpClient) {}

  // method to save an answer (POST)
  saveAnswer(form: any): Observable<any> {
    return this.http.post<any>(this.baseUrl, form);
  }

  // methode to get all answers (GET)
  getAllAnswer(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }

  // (Optionnal)method to get the answer by id (GET)
  getAnswerById(id: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }

   // (Optionnal) methor to retrieve the answers of the form by its id 
   getAnswerByIdForm(id: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/form/${id}`);
  }

  // (Optionnal) method to delete a form (DELETE)
  deleteAnswer(id: string): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/${id}`);
  }
}
