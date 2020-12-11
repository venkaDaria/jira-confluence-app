const config = {
    VERSION: process.env.VERSION
};

export default config;

export const SERVER_API_URL = process.env.SERVER_API_URL;

import moment from 'moment';

export const APP_LOCAL_DATETIME_FORMAT = 'YYYY-MM-DD HH:mm';
export const convertDateTimeFromServer = date => date ? moment(date).format(APP_LOCAL_DATETIME_FORMAT) : null;

export const isPromise = (value): boolean => {
    if (value !== null && typeof value === 'object') {
        return value && typeof value.then === 'function';
    }
    return false;
};
