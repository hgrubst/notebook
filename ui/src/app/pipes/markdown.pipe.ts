import { Pipe, PipeTransform } from '@angular/core';

import * as marked from 'marked';

@Pipe({
  name: 'markdown'
})
export class MarkdownPipe implements PipeTransform {

  transform(value: string, detectOneLine?: boolean): string {
    if (value) {
      if (detectOneLine) {
          const numberOfLines = (value.match(/\n/g) || '').length + 1;
          if (numberOfLines === 1) {
              // Replace wrapping block in marked
              return marked.inlineLexer(value, []);
          }
      }
      return marked(value);
  }
  return value;
  }

}
