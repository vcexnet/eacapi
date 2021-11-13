/* 首页图2 */
var chartDom = document.getElementById('main');
var myChart = echarts.init(chartDom);
var option;

option = {
	color:['#798BE0'],
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ['03-03', '03-04', '03-05', '03-06', '03-07', '03-08', '03-09']
    },
    yAxis: {
        type: 'value'
    },
    series: [{
	
        data: [11820, 11932, 21901, 34934, 21290, 51330, 71320],
        type: 'line',
        areaStyle: {},
		itemStyle: {
		                color: '#001D8F'
		            },
		areaStyle: {
		                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
		                    offset: 0,
		                    color: '#798BE0'
		                }, {
		                    offset: 1,
		                    color: '#ffffff'
		                }])
		            }
    }]
};
myChart.setOption(option);
/* 首页图1 */
var chartDom1 = document.getElementById('main1');
var myChart1 = echarts.init(chartDom1);
var option1;

option1 = {
	color:['#798BE0'],
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ['03-03', '03-04', '03-05', '03-06', '03-07', '03-08', '03-09']
    },
    yAxis: {
        type: 'value'
    },
    series: [{
	
        data: [11820, 11932, 21901, 34934, 21290, 51330, 71320],
        type: 'line',
        areaStyle: {},
		itemStyle: {
		                color: '#001D8F'
		            },
		areaStyle: {
		                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
		                    offset: 0,
		                    color: '#798BE0'
		                }, {
		                    offset: 1,
		                    color: '#ffffff'
		                }])
		            }
    }]
};
myChart1.setOption(option1);
/* 巨鲸追踪 */
var chartDom2 = document.getElementById('main2');
var myChart2 = echarts.init(chartDom2);
var option2;

option2 = {
	
    xAxis: {
        type: 'category',
        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
    },
    yAxis: {
        type: 'value'
    },
    series: [{
        data: [150, 230, 224, 218, 135, 147, 260],
        type: 'line'
    }]
};
myChart2.setOption(option2);
/* 图表 */
var chartDom3 = document.getElementById('Chart11');
var myChart3 = echarts.init(chartDom3);
var option3;

option3 = {
	 title: {
	        left: 'left',
	        text: '每日市值  (亿美元)',
	    },
  color:['#798BE0'],
  xAxis: {
      type: 'category',
      boundaryGap: false,
      data: ['03-03', '03-04', '03-05', '03-06', '03-07', '03-08', '03-09']
  },
  yAxis: {
      type: 'value'
  },
  series: [{
  
      data: [120, 232, 211, 134, 20, 530, 720],
      type: 'line',
      areaStyle: {},
  	itemStyle: {
  	                color: '#001D8F'
  	            },
  	areaStyle: {
  	                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
  	                    offset: 0,
  	                    color: '#798BE0'
  	                }, {
  	                    offset: 1,
  	                    color: '#ffffff'
  	                }])
  	            }
  }]
};
myChart3.setOption(option3);
var chartDom4 = document.getElementById('Chart12');
var myChart4 = echarts.init(chartDom4);
myChart4.setOption(option3);
var chartDom21 = document.getElementById('Chart21');
var myChart21 = echarts.init(chartDom21);
myChart21.setOption(option3);
var chartDom22 = document.getElementById('Chart22');
var myChart22 = echarts.init(chartDom22);
myChart22.setOption(option3);
var chartDom23 = document.getElementById('Chart23');
var myChart23 = echarts.init(chartDom23);
myChart23.setOption(option3);
var chartDom31 = document.getElementById('Chart31');
var myChart31 = echarts.init(chartDom31);
myChart31.setOption(option3);
var chartDom32 = document.getElementById('Chart32');
var myChart32 = echarts.init(chartDom32);
myChart32.setOption(option3);
var chartDom41 = document.getElementById('Chart41');
var myChart41 = echarts.init(chartDom41);
myChart41.setOption(option3);
var chartDom42 = document.getElementById('Chart42');
var myChart42 = echarts.init(chartDom42);
myChart42.setOption(option3);
var chartDom43 = document.getElementById('Chart43');
var myChart43 = echarts.init(chartDom43);
myChart43.setOption(option3);
var chartDom44 = document.getElementById('Chart44');
var myChart44 = echarts.init(chartDom44);
myChart44.setOption(option3);
var chartDom45 = document.getElementById('Chart45');
var myChart45 = echarts.init(chartDom45);
myChart45.setOption(option3);
var chartDom46 = document.getElementById('Chart46');
var myChart46 = echarts.init(chartDom46);
myChart46.setOption(option3);
var chartDom47 = document.getElementById('Chart47');
var myChart47 = echarts.init(chartDom47);
myChart47.setOption(option3);
var chartDom48 = document.getElementById('Chart48');
var myChart48 = echarts.init(chartDom48);
myChart48.setOption(option3);
var chartDom49 = document.getElementById('Chart49');
var myChart49 = echarts.init(chartDom49);
myChart49.setOption(option3);
var chartDom51 = document.getElementById('Chart51');
var myChart51 = echarts.init(chartDom51);
myChart51.setOption(option3);
var chartDom52 = document.getElementById('Chart52');
var myChart52 = echarts.init(chartDom52);
myChart52.setOption(option3);
var chartDom53 = document.getElementById('Chart53');
var myChart53 = echarts.init(chartDom53);
myChart53.setOption(option3);
var chartDom54 = document.getElementById('Chart54');
var myChart54 = echarts.init(chartDom54);
myChart54.setOption(option3);
var chartDom55 = document.getElementById('Chart55');
var myChart55 = echarts.init(chartDom55);
myChart55.setOption(option3);
var chartDom61 = document.getElementById('Chart61');
var myChart61 = echarts.init(chartDom61);
myChart61.setOption(option3);
var chartDom62 = document.getElementById('Chart62');
var myChart62 = echarts.init(chartDom62);
var colors = ['#5470C6', '#EE6666'];
option62 = {
	title: {
	       left: 'left',
	       text: '地址数',
	   },
    color: colors,

    tooltip: {
        trigger: 'none',
        axisPointer: {
            type: 'cross'
        }
    },
    legend: {
        data:['活跃地址数', '新增地址数']
    },
    grid: {
        top: 70,
        bottom: 50
    },
    xAxis: [
        {
            type: 'category',
            axisTick: {
                alignWithLabel: true
            },
            axisLine: {
                onZero: false,
                lineStyle: {
                    color: colors[1]
                }
            },
            axisPointer: {
                label: {
                    formatter: function (params) {
                        return '活跃地址数  ' + params.value
                            + (params.seriesData.length ? '：' + params.seriesData[0].data : '');
                    }
                }
            },
            data: ['2016-1', '2016-2', '2016-3', '2016-4', '2016-5', '2016-6', '2016-7', '2016-8', '2016-9', '2016-10', '2016-11', '2016-12']
        },
        {
            type: 'category',
            axisTick: {
                alignWithLabel: true
            },
            axisLine: {
                onZero: false,
                lineStyle: {
                    color: colors[0]
                }
            },
            axisPointer: {
                label: {
                    formatter: function (params) {
                        return '新增地址数  ' + params.value
                            + (params.seriesData.length ? '：' + params.seriesData[0].data : '');
                    }
                }
            },
            data: ['2015-1', '2015-2', '2015-3', '2015-4', '2015-5', '2015-6', '2015-7', '2015-8', '2015-9', '2015-10', '2015-11', '2015-12']
        }
    ],
    yAxis: [
        {
            type: 'value'
        }
    ],
    series: [
        {
            name: '活跃地址数',
            type: 'line',
            xAxisIndex: 1,
            smooth: true,
            emphasis: {
                focus: 'series'
            },
            data: [2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
        },
        {
            name: '新增地址数',
            type: 'line',
            smooth: true,
            emphasis: {
                focus: 'series'
            },
            data: [3.9, 5.9, 11.1, 18.7, 48.3, 69.2, 231.6, 46.6, 55.4, 18.4, 10.3, 0.7]
        }
    ]
};
myChart62.setOption(option62);
var chartDom71 = document.getElementById('Chart71');
var myChart71 = echarts.init(chartDom71);
myChart71.setOption(option3);
var chartDom81 = document.getElementById('Chart81');
var myChart81 = echarts.init(chartDom81);
myChart81.setOption(option3);
var chartDom91 = document.getElementById('Chart91');
var myChart91 = echarts.init(chartDom91);

option91 = {
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        x: 'left',
        data: ['Top 1-10', 'Top 11-50', 'Top 51-200', 'Others']
    },
    series: [
        {
            name:'访问来源',
            type:'pie',
            selectedMode: 'single',
            radius: [0, '30%'],
 
            label: {
                normal: {
                    position: 'inner'
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data: [
                {value: 1548, name: 'Top 1-10'},
                {value: 775, name: 'Top 11-50'},
                {value: 679, name: 'Top 51-200', },
            	{value: 679, name: 'Others', },
            	// {value: 679, name: '营销广告', selected: true}
            ]
        },
        {
            name:'访问来源',
            type:'pie',
            radius: ['40%', '55%'],
 
            data:[
                {value:335, name:'直达'},
                {value:310, name:'邮件营销'},
                {value:234, name:'联盟广告'},
                {value:135, name:'视频广告'},
                {value:1048, name:'百度'},
                {value:251, name:'谷歌'},
                {value:147, name:'必应'},
                {value:102, name:'其他'}
            ]
        }
    ]
};
 
myChart91.setOption(option91);
var chartDomss = document.getElementById('Chartss');
var myChartss = echarts.init(chartDomss);
myChartss.setOption(option62);

function qiehuan(reg,pro){
	var sy = document.getElementById("sy");
	sy.style.color = "#8E9AB5";
	var jjzz = document.getElementById("jjzz");
	jjzz.style.color = "#8E9AB5";
	var tb = document.getElementById("tb");
	tb.style.color = "#8E9AB5";
	var bk = document.getElementById("bk");
	bk.style.color = "#8E9AB5";
	var fhb = document.getElementById("fhb");
	fhb.style.color = "#8E9AB5";
	var dqrjy = document.getElementById("dqrjy");
	dqrjy.style.color = "#8E9AB5";
	var pro = document.getElementById(pro);
	pro.style.color = "#213654";
	var shouye=document.getElementById("shouye");
	shouye.setAttribute("hidden",true);
	var zhuizong=document.getElementById("zhuizong");
	zhuizong.setAttribute("hidden",true);
	var tubiao=document.getElementById("tubiao");
	tubiao.setAttribute("hidden",true);
	var baokuai=document.getElementById("baokuai");
	baokuai.setAttribute("hidden",true);
	var queren=document.getElementById("queren");
	queren.setAttribute("hidden",true);
	var fuhao=document.getElementById("fuhao");
	fuhao.setAttribute("hidden",true);
	var fuhao=document.getElementById("sousuo");
	fuhao.setAttribute("hidden",true);
	var regid=document.getElementById(reg);
	regid.removeAttribute("hidden");
	
}