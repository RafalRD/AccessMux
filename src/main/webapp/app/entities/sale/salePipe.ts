import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Pipe({
    name: 'saleSafe'
})
export class SalePipe implements PipeTransform {
    constructor(private sanitizer: DomSanitizer) {}
    transform(url) {
        return this.sanitizer.bypassSecurityTrustResourceUrl(url);
    }
}
