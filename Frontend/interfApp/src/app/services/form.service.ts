import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FormService {

  private baseUrl = 'http://localhost:8080/api/forms'; // URL de base pour l'API backend

  constructor(private http: HttpClient) {}

  // Method to save a form (POST)
  saveForm(form: any): Observable<any> {
    return this.http.post<any>(this.baseUrl, form);
  }

  updateForm(form: any): Observable<any> {
    return this.http.put<any>(this.baseUrl, form);
  }

  // method to get all forms (GET)
  getAllForms(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }

  // (Optionnal) method to get all form by its id (GET)
  getFormById(id: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }

  getFormByIdGroup(id: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/group/${id}`);
  }

  // (Optionnal) method to delete a form (DELETE)
  deleteForm(id: string): Observable<any> {
    console.log(id);
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
}
