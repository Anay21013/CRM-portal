import { Injectable } from '@angular/core';
import * as AWS from 'aws-sdk';
import { environment } from '../../environments/environment';

AWS.config.update({
  accessKeyId: environment.AWS_ACCESS_KEY_ID,
  secretAccessKey: environment.AWS_SECRET_ACCESS_KEY,
  region: 'ap-south-1'
});


@Injectable({
  providedIn: 'root'
})
export class StorageService {

  s3 = new AWS.S3();

  uploadFiletoAWS(file: any) {
    const params = {
      Bucket: 'dev-vhub-default',
      Key: `${environment.S3_BUCKET_FOLDER}/${file.name}`,
      Body: file
    };
    return new Promise((resolve, reject) => {
      this.s3.upload(params).promise().then(data => {
        console.log("File uploaded successfully. Location:", data.Location);
        resolve(data);
      }).catch(err => {
        console.log("Error while uploading", err);
        reject(err);
      });
    });
  }

  getFileFromAWS(fileName: any) {
    const params = {
      Bucket: 'dev-vhub-default',
      Key: `${environment.S3_BUCKET_FOLDER}/${fileName.name}`
    };
    return new Promise<Blob>((resolve, reject) => {
      this.s3.getObject(params).promise().then(data => {
        const fileBlob = new Blob([data.Body as BlobPart], { type: data.ContentType });
        console.log("File retrived successfully");
        resolve(fileBlob);
      }).catch(err => {
        console.log("Error while getting file", err);
        reject(err);
      });
    });
  }
}







