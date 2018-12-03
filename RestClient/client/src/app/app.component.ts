import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

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
  errorHttp = false;

  constructor(private http: HttpClient) { }

  public addItem() {
    if(this.name === "" || this.qty === 0 || this.price === 0.0 || +this.qty === 0 || +this.price === 0) {
      this.error = true;
      return;
    }
    this.error = false;
    this.errorHttp = false;
    this.total += this.qty * this.price;
    this.panier.push({ name: this.name, qty: this.qty, price: this.price });
    this.name = '';
    this.price = 0.0;
    this.qty = 0;
  }

  public async sendReceipt() {
    if (this.panier.length === 0) return;
    try {
      const headers = new HttpHeaders({'Content-Type':'application/json; charset=utf-8', 'Access-Control-Allow-Origin': '*'});
      var result = await this.http.post(
        'http://192.168.56.102:8080/TP4/resources/REST/receipt',
        this.panier,
        {headers: headers}
      ).toPromise();
      this.errorHttp = false;
      this.panier = [];
      this.total = 0;
    } catch (err) {
      console.error(err);
      this.errorHttp = true;
      this.panier = [];
      this.total = 0;
    }
  }
}
