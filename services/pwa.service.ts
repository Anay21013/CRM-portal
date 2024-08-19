import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { SwUpdate } from '@angular/service-worker';
import { PopUpComponent } from '../common-utils/components/pop-up/pop-up.component';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PwaService {

  public promptEvent: any;
  public isAppInstalled: boolean = false;

  constructor(
    private readonly swUpdate: SwUpdate,
    public dialog: MatDialog
  ) {
    this.checkAppInstalled();
    this.checkForOnAppInstallation();
    this.susbscribeForUpdates();
  }

  checkAppInstalled() {
    if (window.matchMedia('(display-mode: standalone)').matches) {
      this.isAppInstalled = true;
      this.promptEvent = null;
    } else {
      this.isAppInstalled = false;
      this.enableManualInstall();
    }
    console.log('display-mode is standalone: ', this.isAppInstalled);
  }

  enableManualInstall() {
    window.addEventListener('beforeinstallprompt', event => {
      this.promptEvent = event;
    });
  }

  checkForOnAppInstallation() {
    window.addEventListener('appinstalled', (_evt) => {
      console.log('vhub installed !');
      this.isAppInstalled = true;
      this.promptEvent = null;
    });
  }

  susbscribeForUpdates() {
    if (environment.production) {
      setInterval(() => {
        this.swUpdate.checkForUpdate().then(res => {
          if (res) {
            this.dialog.closeAll();
            this.dialog.open(PopUpComponent, {
              width: '400px',
              enterAnimationDuration: '700ms',
              exitAnimationDuration: '300ms',
              data: {
                header: 'New Version Available!',
                text: `To get up to date click on Refresh`,
                yes: {
                  text: 'Refresh',
                  color: 'primary',
                  action: async () => {
                    const keys = await window.caches.keys();
                    await Promise.all(keys.map(key => caches.delete(key)));
                    window.location.reload();
                  }
                }
              },
              backdropClass: 'blurBackdrop',
              disableClose: true
            });
          }
        });
      }, environment.CHECK_FOR_UPDATE_TIMEOUT);
    }
  }

}
