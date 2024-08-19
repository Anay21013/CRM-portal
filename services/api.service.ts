import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { environment } from 'projects/vhub/src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  headers = {
    headers: new HttpHeaders()
  };

  constructor(private readonly http: HttpClient) { }

  get(url: any, _data: Object = {}): Observable<any> {
    return this.http.get(environment.api_url + url, this.headers).pipe(map(res => {
      return res;
    }));
  }
  post(url: any, data: any = null): Observable<any> {

    const curUserDetail = JSON.parse(localStorage.getItem('authentication'));
    if (curUserDetail != null && curUserDetail.token == "") {
      return this.http.post(environment.api_url + url, data).pipe(map(res => { return res; }));
    }
    else {
      return this.http.post(environment.api_url + url, data, this.headers).pipe(map(res => {
        return res;
      }));
    }
  }
  put(url: any, data: any = null) {
    return this.http.put(environment.api_url + url, data, this.headers).pipe(map(res => {
      return res;
    }));
  }
  delete(url: string) {
    return this.http.delete(environment.api_url + url, this.headers).pipe(map(res => {
      return res;
    }));
  }
}
