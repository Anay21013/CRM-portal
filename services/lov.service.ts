import { Injectable } from '@angular/core';
import { of } from 'rxjs';
import { ApiService } from './api.service';

class Lov {
  code: number;
  text: string;
  displayText: string;
  shortText: string;

  deserialize(input: any): this {
    if (input) {
      this.code = input.code;
      this.text = input.type;
      this.displayText = input.description;
      this.shortText = input.shortText;
    }
    return this;
  }
}

@Injectable({
  providedIn: 'root'
})
export class LovService {
  private lovList: any[];

  constructor(
    private readonly apiService: ApiService
  ) { }

  public set lov(values: any) {
    this.lovList = values;
  }

  public get lov(): any {
    return this.lovList;
  }

  getLovList(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.apiService.get('lov').subscribe({
        next: (res) => {
          if (res) {
            if (this.lov) {
              resolve(of(this.lov));
            }
            else {
              const keys = Object.keys(res);
              const newObj = {};
              keys.map((key) => {
                if (key != "commonLovs") {
                  newObj[key] = res[key].map((value) => new Lov().deserialize(value));

                }
                else {
                  const commonKeys = Object.keys(res[key]);
                  commonKeys.map((commonkey) => {
                    newObj[commonkey] = res[key][commonkey].map((value) => new Lov().deserialize(value));
                  });
                }
              });
              this.lov = newObj;
              resolve(newObj);
            }
          }
        }, error: (error) => {
          reject(error?.error);
        }
      });
    });
  }


  getDisplayText(type: string, code: number): string {
    const values = this.lov?.hasOwnProperty(type) ? this.lov[type] : null;
    return values?.find((value) => (value.code === code || value.text === code))?.displayText || '';
  }

  getShortText(type: string, code: number): string {
    const values = this.lov?.hasOwnProperty(type) ? this.lov[type] : null;
    return values?.find((value) => (value.code === code || value.text === code))?.shortText || '';
  }

  getLov(type: string, code: number): string {
    const values = this.lov?.hasOwnProperty(type) ? this.lov[type] : null;
    return values?.find((value) => value.code === code) || { text: 'NA' };
  }

  getCodeFromDisplayText(type: string, text: string): string {
    const values = this.lov?.hasOwnProperty(type) ? this.lov[type] : null;
    return values?.find((value) => value.displayText === text)?.code || '';
  }

  getText(type: string, code: number): string {
    const values = this.lov?.hasOwnProperty(type) ? this.lov[type] : null;
    return values?.find((value) => value.code === code)?.text || '';
  }


  cleardata(): void {
    this.lovList = undefined;
  }

  sortLovList(a, b) {
    const name1 = a.code;
    const name2 = b.code;

    let comparison = 0;

    if (name1 > name2) {
      comparison = 1;
    } else {
      if (name1 < name2) {
        comparison = -1;
      }
    }
    return comparison;
  }
}
