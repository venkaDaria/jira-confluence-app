import { IDiagram } from 'app/shared/model/diagram.model';
import axios, { AxiosPromise } from 'axios';
import { SERVER_API_URL } from 'app/config/constants';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

export const ACTION_TYPES = {
    FETCH_DIAGRAM_DATA: 'diagram/FETCH_DIAGRAM_DATA',
    RESET: 'diagram/RESET'
};

const initialState = {
    loading: false,
    errorMessage: null,
    dataChart: null,
    totalItems: 0
};

export type DiagramState = Readonly<typeof initialState>;

// Reducer

export default (state: DiagramState = initialState, action): DiagramState => {
    switch (action.type) {
        case REQUEST(ACTION_TYPES.FETCH_DIAGRAM_DATA):
            return {
                ...state,
                errorMessage: null,
                loading: true
            };
        case FAILURE(ACTION_TYPES.FETCH_DIAGRAM_DATA):
            return {
                ...state,
                loading: false,
                errorMessage: action.payload
            };
        case SUCCESS(ACTION_TYPES.FETCH_DIAGRAM_DATA):
            return {
                ...state,
                loading: false,
                totalItems: action.payload.headers['x-total-count'],
                dataChart: action.payload.data
            };
        case ACTION_TYPES.RESET:
            return {
                ...initialState
            };
        default:
            return state;
    }
};

const apiUrl = SERVER_API_URL + 'api/diagram';

// Actions

export interface IPayload<T> {
    type: string;
    payload: AxiosPromise<T>;
    meta?: any;
}

export declare type ICrudGetAction<T> = (name: string) => IPayload<T> | ((dispatch: any) => IPayload<T>);

export const getChartData: ICrudGetAction<IDiagram> = name => {
    const requestUrl = `${apiUrl}/${name}`;
    return {
        type: ACTION_TYPES.FETCH_DIAGRAM_DATA,
        payload: axios.get<IDiagram>(requestUrl + `?cacheBuster=${new Date().getTime()}`)
    };
};

export const reset = () => ({
    type: ACTION_TYPES.RESET
});
