import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { VeiculoListComponent } from './components/veiculo-list/veiculo-list.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive, VeiculoListComponent, DashboardComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Sistema de Gerenciamento de Veículos';
  currentView = 'veiculos';

  showVeiculos(): void {
    this.currentView = 'veiculos';
  }

  showDashboard(): void {
    this.currentView = 'dashboard';
  }
}