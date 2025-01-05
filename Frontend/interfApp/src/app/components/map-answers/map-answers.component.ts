import { Component, Input, OnInit } from '@angular/core';
import * as L from 'leaflet'; // Import Leaflet

@Component({
  selector: 'app-map-answers',
  imports: [],
  templateUrl: './map-answers.component.html',
  styleUrl: './map-answers.component.css'
})
export class MapAnswersComponent implements OnInit {

  private map!: L.Map;
  private marqueur!: L.Marker;
  @Input() answersCoordinates: any[] = [] ;
  
    async ngOnInit(): Promise<void> {

      if (typeof window !== 'undefined') {
        const L = await import('leaflet'); // Dynamic import
  
        this.map = L.map('map').setView([50.41136, 4.44448], 10); // centered on Charleroi 
  
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
          maxZoom: 19,
          attribution: '&copy; OpenStreetMap contributors'
        }).addTo(this.map);
      
      if (this.answersCoordinates.length > 0) { // verify if the coordinates is not empty 
        //for each coordinate, put a marker.
        this.answersCoordinates.forEach(answerCoord => {
          this.marqueur = L.marker([answerCoord.lat, answerCoord.lng])
          .addTo(this.map)
          .bindPopup(`Coordonnées : ${answerCoord.lat}, ${answerCoord.lng}`)
        });
        
      }
    }
  }


}
