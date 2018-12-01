import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'client';
  panier = [];
  total = 0;
  name = '';
  price = 0.0;
  qty = 0;
  error = false;
  success = false;
  errorHttp = false;

  constructor(private http: HttpClient) { }

  public addItem() {
    if(this.name === "" || this.qty === 0 || this.price === 0.0 || +this.qty === 0 || +this.price === 0) {
      this.error = true;
      return;
    }
    this.success = undefined;
    this.error = false;
    this.total += this.qty * this.price;
    this.panier.push({ name: this.name, qty: this.qty, price: this.price });
    this.name = '';
    this.price = 0.0;
    this.qty = 0;
  }

  public async sendReceipt() {
    if (this.panier.length === 0) return;
    try {
      var result = await this.http.post('http://localhost:8080/TP4/resources/REST/receipt', this.panier).toPromise();
      this.success = true;
      this.errorHttp = false;
      this.panier = [];
    } catch (err) {
      this.errorHttp = true;
      this.success = false;
    }
  }
}
