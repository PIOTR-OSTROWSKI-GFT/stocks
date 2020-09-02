import ArrowUpwardIcon from '@material-ui/icons/ArrowUpward';
import ArrowDownwardIcon from '@material-ui/icons/ArrowDownward';
import FiberManualRecordIcon from '@material-ui/icons/FiberManualRecord';
import React from "react";

function Trend(trend) {
    switch (trend.trend) {
        case 'UP':
            return (<ArrowUpwardIcon style={{ color: 'green' }}/>);
        case 'DOWN':
            return (<ArrowDownwardIcon style={{ color: 'red' }}/>);
            return (<ArrowDownwardIcon style={{ color: 'red' }}/>);
        case 'STATUS_QUO':
        default:
            return (<FiberManualRecordIcon />);
    }
}

export default Trend;