import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoaderService {
  private IsRouterChange = false;
  private isLoading = false;
  loadingStatus: Subject<boolean> = new Subject();
  resetTimer: Subject<any> = new Subject<any>();
  loaderText = '';
  public popupShown = false;

  /**
   * @description
   * This method returns the loading status of loader
   */
  get loading(): boolean {
    return this.isLoading;
  }

  /**
   * @description
   * This method sets the loading status and loading description text of loader
   */
  set loading(value) {
    this.isLoading = value;
    if (this.isLoading) {
      this.resetTimer.next(null);
    }
    this.loadingStatus.next(value);
  }

  set isRouterChangTrigger(val: boolean) {
    this.IsRouterChange = val;
  }

  get isRouterChangTrigger(): boolean {
    return this.IsRouterChange;
  }

  /**
   * @description
   * This method triggers the loader
   */
  startLoading(): void {
    this.loading = true;
  }

  /**
   * @description
   * This method stops loader
   */
  stopLoading(): void {
    this.loading = false;
  }

  /**
   * @description
   * This method sets the loading description text to the loader
   */
  setLoadingText(
    show: boolean,
    loaderText?: string,
    popupShown: boolean = false
  ): void {
    this.loading = show;
    this.loaderText = loaderText;
    this.popupShown = popupShown;
  }

  /**
   * @description
   * This method returns the loading description text of the loader
   */
  getLoadingText(): string {
    return this.loaderText;
  }
}
