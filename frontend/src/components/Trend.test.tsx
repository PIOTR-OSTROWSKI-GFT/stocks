import React from 'react';
import Trend from './Trend';
import ArrowUpwardIcon from '@material-ui/icons/ArrowUpward';

test('returns proper trend icon', () => {
    const iconResult = Trend({"trend":"UP"});
    expect(iconResult.type.displayName).toBe("ArrowUpwardIcon");
});
