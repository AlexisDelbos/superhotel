import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HotelsComponent } from './components/hotels/hotels.component';
import { HotelComponent } from './components/hotel/hotel.component';
import { LoginoutComponent } from './components/loginout/loginout.component';
import { EdithotelComponent } from './components/edithotel/edithotel.component';
import { AdminComponent } from './components/admin/admin.component';
import { ManagerComponent } from './components/manager/manager.component';
import { AdminGuard } from './guards/admin.guard';
import { CityComponent } from './components/city/city.component';

const routes: Routes = [
  { path: '', redirectTo: 'hotels', pathMatch: 'full' },
  { path: 'hotels', component: HotelsComponent },
  { path: 'hotel/:id', component: HotelComponent },
  { path: 'login', component: LoginoutComponent },
  { path: 'edithotel/:id', component: EdithotelComponent , canActivate: [AdminGuard]},
  { path: 'admin', component: AdminComponent, canActivate: [AdminGuard] },
  { path: 'edithotel', component: EdithotelComponent, canActivate: [AdminGuard] },
  { path: 'manager', component: ManagerComponent , canActivate: [AdminGuard]},
  { path : 'city', component: CityComponent, canActivate: [AdminGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
