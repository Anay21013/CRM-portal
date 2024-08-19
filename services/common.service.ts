import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Subject, lastValueFrom } from 'rxjs';
import { PopUpComponent } from '../common-utils/components/pop-up/pop-up.component';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  resetFileUpload: Subject<any> = new Subject<any>();

  constructor(
    private readonly dialog: MatDialog
  ) { }

  public static getAttributeValue(obj, keys) {
    if (keys) {
      if (keys.indexOf("[") > -1 && keys.indexOf("]") > -1) {
        return this.jsonPathToValue(obj, keys);
      }
      return keys.split(".").reduce(
        (xs, x) => (xs?.[x] !== null && xs[x] !== undefined ? xs[x] : null),
        obj
      );
    }
    return null;
  }
  private static jsonPathToValue(jsonData, path) {
    // convert indexes to properties
    path = path.replace(/\[(\w+)\]/g, '.$1');
    // strip a leading dot
    path = path.replace(/^\./, '');
    const pathArray = path.split('.');
    for (let i = 0, n = pathArray.length; i < n; ++i) {
      const key = pathArray[i];
      if (key in jsonData) {
        if (jsonData[key] !== null) {
          jsonData = jsonData[key];
        } else {
          return null;
        }
      } else {
        return key;
      }
    }
    return jsonData;
  }

  async openConfirm(headerText = 'Unsaved Changes', message: string = 'You have unsaved changes. Do you really want to leave?'): Promise<boolean> {
    const dialogRef = this.dialog.open(PopUpComponent, {
      width: '400px',
      enterAnimationDuration: '700ms',
      exitAnimationDuration: '300ms',
      data: {
        header: headerText,
        text: message,
        yes: {
          text: 'Yes',
          color: 'primary',
          action: () => dialogRef.close(true)
        },
        no: {
          text: 'No',
          action: () => dialogRef.close(false)
        }
      },
      backdropClass: 'blurBackdrop',
      disableClose: true
    });
    return lastValueFrom(dialogRef.afterClosed());
  }
}
