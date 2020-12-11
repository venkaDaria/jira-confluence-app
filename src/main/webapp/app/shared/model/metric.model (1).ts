export interface IMetric {
    id?: number;
    name?: string;
    rate?: string;
    enabled?: boolean;
    custom?: string;
}

export interface IReportedWorklog {
    expectedSprintHours?: number;
    actualSprintHours?: number;
    expectedYesterdayHours?: number;
    actualYesterdayHours?: number;
}

export const enum MetricStatus {
    GREEN = 'GREEN',
    YELLOW = 'YELLOW',
    RED = 'RED'
}

export const enum SprintIssues {
    CURRENT = 'CURRENT',
    NOT_CURRENT = 'NOT_CURRENT',
    ALL = 'ALL'
}

export const enum IssueStatus {
    ALL = 'All',
    TO_DO = 'To Do',
    OPEN = 'Open',
    IN_PROGRESS = 'In Progress',
    IN_TESTING = 'In Testing',
    DELIVERED = 'Delivered',
    READY_FOR_DEMO = 'Ready for Demo',
    CLOSED = 'Closed'
}
