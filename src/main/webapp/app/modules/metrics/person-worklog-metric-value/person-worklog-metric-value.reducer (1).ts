import axios, { AxiosPromise } from 'axios';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

import {
    IPersonWorklogMetricValue,
    PersonWorklogDefaultValue as defaultValue
} from 'app/shared/model/metrics-metric.model';

import { SERVER_API_URL } from 'app/config/constants';

export const ACTION_TYPES = {
    FETCH_PERSON_WORKLOG_METRIC_VALUE_LIST: 'personWorklogMetricValue/FETCH_PERSON_WORKLOG_METRIC_VALUE_LIST',
    FETCH_PERSON_WORKLOG_METRIC_VALUE: 'personWorklogMetricValue/FETCH_PERSON_WORKLOG_METRIC_VALUE',
    RESET: 'personWorklogMetricValue/RESET'
};

const initialState = {
    loading: false,
    errorMessage: null,
    entities: [] as ReadonlyArray<IPersonWorklogMetricValue>,
    entity: defaultValue,
    totalItems: 0
};

export type PersonWorklogMetricValueState = Readonly<typeof initialState>;

// Reducer

export default (state: PersonWorklogMetricValueState = initialState, action): PersonWorklogMetricValueState => {
    switch (action.type) {
        case REQUEST(ACTION_TYPES.FETCH_PERSON_WORKLOG_METRIC_VALUE_LIST):
        case REQUEST(ACTION_TYPES.FETCH_PERSON_WORKLOG_METRIC_VALUE):
            return {
                ...state,
                errorMessage: null,
                loading: true
            };
        case FAILURE(ACTION_TYPES.FETCH_PERSON_WORKLOG_METRIC_VALUE_LIST):
        case FAILURE(ACTION_TYPES.FETCH_PERSON_WORKLOG_METRIC_VALUE):
            return {
                ...state,
                loading: false,
                errorMessage: action.payload
            };
        case SUCCESS(ACTION_TYPES.FETCH_PERSON_WORKLOG_METRIC_VALUE_LIST):
            return {
                ...state,
                loading: false,
                totalItems: action.payload.headers['x-total-count'],
                entities: action.payload.data
            };
        case SUCCESS(ACTION_TYPES.FETCH_PERSON_WORKLOG_METRIC_VALUE):
            return {
                ...state,
                loading: false,
                entity: action.payload.data
            };
        case ACTION_TYPES.RESET:
            return {
                ...initialState
            };
        default:
            return state;
    }
};

const apiUrl = SERVER_API_URL + 'api/person-worklog';

// Actions

export interface IPayload<T> {
    type: string;
    payload: AxiosPromise<T>;
    meta?: any;
}

export declare type ICrudGetAction<T> = (id: string | number) => IPayload<T> | ((dispatch: any) => IPayload<T>);
export declare type ICrudGetAllAction<T> = () => IPayload<T> | ((dispatch: any) => IPayload<T>);

export const getEntities: ICrudGetAllAction<IPersonWorklogMetricValue> = () => ({
    type: ACTION_TYPES.FETCH_PERSON_WORKLOG_METRIC_VALUE_LIST,
    payload: axios.get<IPersonWorklogMetricValue>(apiUrl + `?cacheBuster=${new Date().getTime()}`)
});

export const collect = metricName => {
    const requestUrl = `${apiUrl}/${metricName}`;
    axios.post(requestUrl);
};

export const getEntity: ICrudGetAction<IPersonWorklogMetricValue> = id => {
    const requestUrl = `${apiUrl}/${id}`;
    return {
        type: ACTION_TYPES.FETCH_PERSON_WORKLOG_METRIC_VALUE,
        payload: axios.get<IPersonWorklogMetricValue>(requestUrl + `?cacheBuster=${new Date().getTime()}`)
    };
};

export const reset = () => ({
    type: ACTION_TYPES.RESET
});
