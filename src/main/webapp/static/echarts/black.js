(function (root, factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as an anonymous module.
        define(['exports', 'echarts'], factory);
    } else if (typeof exports === 'object' && typeof exports.nodeName !== 'string') {
        // CommonJS
        factory(exports, require('echarts'));
    } else {
        // Browser globals
        factory({}, root.echarts);
    }
}(this, function (exports, echarts) {
    var log = function (msg) {
        if (typeof console !== 'undefined') {
            console && console.error && console.error(msg);
        }
    };
    if (!echarts) {
        log('ECharts is not Loaded');
        return;
    }
    var contrastColor = '#eee';
    var axisCommon = function () {
        return {
            axisLine: {
                lineStyle: {
                    color: contrastColor
                }
            },
            axisTick: {
                lineStyle: {
                    color: contrastColor
                }
            },
            axisLabel: {
                textStyle: {
                    color: contrastColor
                }
            },
            splitLine: {
                lineStyle: {
                    type: 'dashed',
                    color: '#aaa'
                }
            }
        };
    };

    var colorPalette = [
		"#d95850",
		"#eb8146",
		"#ffb248",
		"#f2d643",
		"#ebdba4",
		'#8C5A75','#457E9B','#3E6F7E',
		'#dd6b66','#759aa0','#e69d87','#8dc1a9','#ea7e53',
		'#eedd78','#73a373','#73b9bc','#7289ab', '#91ca8c',
		'#f49f42',
		'#2ec7c9','#b6a2de','#5ab1ef','#ffb980','#d87a80'
	];
    var theme = {
        color: colorPalette,
        backgroundColor: '#424e5b',
        tooltip: {
            axisPointer: {
                lineStyle: {
                    color: contrastColor
                },
                crossStyle: {
                    color: contrastColor
                }
            }
        },
        legend: {
            textStyle: {
                color: contrastColor
            }
        },
        textStyle: {
            color: contrastColor
        },
        title: {
            textStyle: {
                color: contrastColor
            }
        },
        toolbox: {
            iconStyle: {
                normal: {
                    borderColor: contrastColor
                }
            }
        },
		dataZoom: {
			textStyle:{color:contrastColor},
            dataBackgroundColor: '#6B668F',
            fillerColor: 'rgba(182,162,222,0.2)',
            handleColor: '#A78ADE'
        },
        timeline: {
            lineStyle: {
                color: contrastColor
            },
            itemStyle: {
                normal: {
                    color: colorPalette[1]
                }
            },
            label: {
                normal: {
                    textStyle: {
                        color: contrastColor
                    }
                }
            },
            controlStyle: {
                normal: {
                    color: contrastColor,
                    borderColor: contrastColor
                }
            }
        },
        timeAxis: axisCommon(),
        logAxis: axisCommon(),
        valueAxis: axisCommon(),
        categoryAxis: axisCommon(),

        line: {
            symbol: 'circle'
        },
        graph: {
            color: colorPalette
        },
        gauge: {
            title: {
                textStyle: {
                    color: contrastColor
                }
            }
        },
        candlestick: {
            itemStyle: {
                normal: {
                    color: '#FD1050',
                    color0: '#0CF49B',
                    borderColor: '#FD1050',
                    borderColor0: '#0CF49B'
                }
            }
        },
        
        bar:{
            label: {
                normal: {
                    textStyle: {
                        color: 'white'
                    }
                }
            }
        }
    };
    theme.categoryAxis.splitLine.show = false;
    echarts.registerTheme('myStyle', theme);
}));