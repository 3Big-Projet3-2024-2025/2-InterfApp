import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FieldType, FormlyModule } from '@ngx-formly/core';
import * as L from 'leaflet'; // Importer Leaflet

@Component({
  selector: 'app-map',
  standalone: true,
  imports:[CommonModule,ReactiveFormsModule, FormsModule, FormlyModule],
  templateUrl: './formly-map-type.component.html',
  styleUrls: ['./formly-map-type.component.css']
})
export class FormlyMapTypeComponent extends FieldType implements OnInit{
  private map!: L.Map;
  private marqueur!: L.Marker ;
  selectedCoords: { lat: number, lng: number } | null = null; // Coordonnées sélectionnées

  get control(): FormControl {
    return this.formControl as FormControl;
  }

  async ngOnInit(): Promise<void> {
    if (typeof window !== 'undefined') {
      const L = await import('leaflet'); // Import dynamique

      this.map = L.map('map').setView([50.41136, 4.44448], 10); // centrée sur charleroi

      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; OpenStreetMap contributors'
      }).addTo(this.map);

      // Ajouter un gestionnaire d'événements pour capturer les clics sur la carte
      this.map.on('click', (event: L.LeafletMouseEvent) => {
        if(this.marqueur){
          this.marqueur.remove();
        }
        const { lat, lng } = event.latlng; // Extraire latitude et longitude
        this.selectedCoords = { lat, lng }; // Stocker les coordonnées sélectionnées
        this.control.setValue(this.selectedCoords);

        if (this.map) { // Vérifier que la carte est initialisée
          this.marqueur = L.marker([lat, lng])
            .addTo(this.map)
            .bindPopup(`Coordonnées : ${lat}, ${lng}`)
            .openPopup();
        }
      });
    }
  }
}

