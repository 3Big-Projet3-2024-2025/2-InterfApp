import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FormService {

  private baseUrl = 'http://localhost:8080/api/forms'; // URL de base pour l'API backend

  constructor(private http: HttpClient) {}

  // Méthode pour sauvegarder un formulaire (POST)
  saveForm(form: any): Observable<any> {
    return this.http.post<any>(this.baseUrl, form);
  }

  // Méthode pour récupérer tous les formulaires (GET)
  getAllForms(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }

  // (Optionnel) Méthode pour récupérer un formulaire par ID (GET)
  getFormById(id: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }

  // (Optionnel) Méthode pour supprimer un formulaire (DELETE)
  deleteForm(id: string): Observable<any> {
    console.log(id);
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
}
