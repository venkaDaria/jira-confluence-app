import { IMetric, IReportedWorklog, IssueStatus, MetricStatus } from 'app/shared/model//metric.model';

export interface IComment {
    author?: string;
    text?: string;
    dateTime?: number;
}

export interface Issue {
    status: IssueStatus;
    key?: string;
    summary?: string;
    assignee?: string;
    type?: string;
    dateTime?: number;
    lastComment?: IComment;
}

interface IMetricValue<T> {
    id?: string;
    calculationTime?: number;
    result?: T;
    status?: MetricStatus;
    metric?: IMetric;
}

interface IUserInfo {
    login?: String;
    displayName?: String;
    issues?: Issue[];
    status?: MetricStatus;
    hoursSpent: number;
}

export interface INotUpdatedIssuesMetricValue extends IMetricValue<number> {
    currentIssues?: Issue[];
    otherIssues?: Issue[];
}

export interface ICustomMetricValue extends IMetricValue<number> {
    currentIssues?: Issue[];
    otherIssues?: Issue[];
}

export interface IUpdatedIssuesMetricValue extends IMetricValue<number> {
    currentIssues?: Issue[];
    otherIssues?: Issue[];
}

export interface IssuesPerPersonMetricValue extends IMetricValue<Map<IssueStatus, number>> {
    userInfos?: IUserInfo[];
}

export interface IPersonWorklogMetricValue extends IMetricValue<IReportedWorklog> {
    userInfos?: IUserInfo[];
}

export const ICustomDefaultValue: Readonly<ICustomMetricValue> = {};
export const INotUpdatedIssuesDefaultValue: Readonly<INotUpdatedIssuesMetricValue> = {};
export const IUpdatedIssuesDefaultValue: Readonly<IUpdatedIssuesMetricValue> = {};
export const IssuesPerPersonDefaultValue: Readonly<IssuesPerPersonMetricValue> = {};
export const PersonWorklogDefaultValue: Readonly<IPersonWorklogMetricValue> = {};
