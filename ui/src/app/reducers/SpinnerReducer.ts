import { Action } from '@ngrx/store';


export const SpinnerReducer = function (state: boolean, action: Action) {


    if (action.type.indexOf('Request') !== -1) {
        return true;
    }
    if (action.type.indexOf('Failure') !== -1 || action.type.indexOf('Success') !== -1) {
        return false;
    }

    return state;

}