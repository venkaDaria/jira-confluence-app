import React from 'react';

import $ from 'jquery';
import 'datatables.net';

import 'bootstrap/dist/css/bootstrap.css';
import 'datatables.net-bs4/js/dataTables.bootstrap4';
import 'datatables.net-bs4/css/dataTables.bootstrap4.css';

import { Issue } from 'app/shared/model/metrics-metric.model';
import { convertDateTimeFromServer } from 'app/config/constants';

import { Tabs, TabList, TabPanel, Tab } from 'react-tabs';
import 'react-tabs/style/react-tabs.css';

import ReactChartkick, { PieChart } from 'react-chartkick';
import Chart from 'chart.js';

ReactChartkick.addAdapter(Chart);

export interface IssuesTableState {
    issues: Issue[];
}

export default class IssuesTable extends React.Component<IssuesTableState> {
    state: IssuesTableState = {
        issues: this.props.issues
    };

    componentWillUnmount() {
        $('.data-table-wrapper')
            .find('table')
            .DataTable()
            .destroy(true);
    }

    shouldComponentUpdate() {
        return false;
    }

    groupBy(list, props) {
        const arr = list.reduce((a, b) => {
            (a[b[props]] = a[b[props]] || []).push(b);
            return a;
        }, {});

        Object.keys(arr).map(key => {
            arr[key] = arr[key].length;
        });

        return Object.entries(arr);
    }

    render() {
        return (
            <Tabs>
                <TabList>
                    <Tab>Table</Tab>
                    <Tab>Graph</Tab>
                </TabList>
                <TabPanel>
                    <div>
                        <table className="table table-bordered table-hover" ref={elem => $(elem).DataTable({
                            data: this.state.issues,
                            columns
                        })}/>
                    </div>
                </TabPanel>
                <TabPanel>
                    <div>
                        <PieChart data={ this.groupBy(this.state.issues, 'status') } />
                    </div>
                </TabPanel>
            </Tabs>
        );
    }
}

const columns = [{
    data: 'key',
    title: 'Issue Key',
    render: key => `<a href=${'/api/jira/' + key}>${key}</a>`
},
    {
        data: 'summary',
        title: 'Summary'
    },
    {
        data: 'status',
        title: 'Status'
    },
    {
        data: 'type',
        title: 'Issue Type'
    },
    {
        data: 'assignee',
        title: 'Assignee'
    },
    {
        data: 'lastUpdate',
        title: 'Last Updated Date',
        render: date => JSON.stringify(date)
    },
    {
        data: 'lastComment',
        title: 'Last Comment',
        render: comment => comment ? comment.text : ''
    },
    {
        data: 'lastComment',
        title: 'Last Comment Author',
        render: comment => comment ? comment.author : ''
    },
    {
        data: 'lastComment',
        title: 'Last Comment Date',
        render: comment => comment ? convertDateTimeFromServer(comment.dateTime) : ''
    }];
