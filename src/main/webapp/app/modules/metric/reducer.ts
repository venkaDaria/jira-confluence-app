import axios from 'axios';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

import { IMetric } from 'app/shared/model/metric.model';
import { SERVER_API_URL } from 'app/config/constants';

export const ACTION_TYPES = {
    FETCH_METRIC_LIST: 'metric/FETCH_METRIC_LIST',
    DELETE_METRIC: 'metric/DELETE_METRIC',
    UPDATE_METRIC: 'metric/UPDATE_METRIC',
    RESET: 'metric/RESET'
};

const initialState = {
    loading: false,
    errorMessage: null,
    entities: [] as ReadonlyArray<IMetric>
};

export type MetricState = Readonly<typeof initialState>;

// Reducer
export default (state: MetricState = initialState, action): MetricState => {
    switch (action.type) {
        case REQUEST(ACTION_TYPES.FETCH_METRIC_LIST):
            return {
                ...state,
                errorMessage: null,
                loading: true
            };
        case FAILURE(ACTION_TYPES.FETCH_METRIC_LIST):
            return {
                ...state,
                loading: false,
                errorMessage: action.payload
            };
        case SUCCESS(ACTION_TYPES.FETCH_METRIC_LIST):
            return {
                ...state,
                loading: false,
                entities: action.payload.data
            };
        case REQUEST(ACTION_TYPES.DELETE_METRIC):
            return {
                ...state,
                errorMessage: null,
                loading: true
            };
        case FAILURE(ACTION_TYPES.DELETE_METRIC):
            return {
                ...state,
                loading: false,
                errorMessage: action.payload
            };
        case SUCCESS(ACTION_TYPES.DELETE_METRIC):
            return {
                ...state,
                loading: false,
                entities: action.payload.data
            };
        case REQUEST(ACTION_TYPES.UPDATE_METRIC):
            return {
                ...state,
                errorMessage: null,
                loading: true
            };
        case FAILURE(ACTION_TYPES.UPDATE_METRIC):
            return {
                ...state,
                loading: false,
                errorMessage: action.payload
            };
        case SUCCESS(ACTION_TYPES.UPDATE_METRIC):
            return {
                ...state,
                loading: false,
                entities: action.payload.data
            };
        case ACTION_TYPES.RESET:
            return {
                ...initialState
            };
        default:
            return state;
    }
};

const apiUrl = SERVER_API_URL + 'api/metrics';

// Actions
export const getEntities = () => ({
    type: ACTION_TYPES.FETCH_METRIC_LIST,
    payload: axios.get<IMetric>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const deleteMetric = metricName => ({
    type: ACTION_TYPES.DELETE_METRIC,
    payload: axios.delete(`${apiUrl}/` + metricName)
});

export const updateMetric = (row, enabled, rate_new) => ({
    type: ACTION_TYPES.UPDATE_METRIC,
    payload: axios.put(apiUrl, {
        name: row.name,
        enabled,
        rate: rate_new,
        custom: row.custom
    })
});

export const reset = () => ({
    type: ACTION_TYPES.RESET
});
