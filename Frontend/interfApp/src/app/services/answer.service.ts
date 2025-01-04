import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AnswerService {

  private baseUrl = 'http://localhost:8080/api/answers'; // URL de base pour l'API backend

  constructor(private http: HttpClient) {}

  // Méthode pour sauvegarder un formulaire (POST)
  saveAnswer(form: any): Observable<any> {
    return this.http.post<any>(this.baseUrl, form);
  }

  // Méthode pour récupérer tous les formulaires (GET)
  getAllAnswer(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }

  // (Optionnel) Méthode pour récupérer un formulaire par ID (GET)
  getAnswerById(id: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }

   // (Optionnel) Méthode pour retrieve the answers of the form by its id 
   getAnswerByIdForm(id: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/form/${id}`);
  }

  // (Optionnel) Méthode pour supprimer un formulaire (DELETE)
  deleteAnswer(id: string): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/${id}`);
  }
}
