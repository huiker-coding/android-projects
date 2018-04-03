var isTargetSearched = false;  //�ж�Ŀ�������Ƿ�������
var restScanCount = getRandomNum(2, 8);  //�õ�Ŀ�����������ɺ���������������������

/**
 *  ͨ����Ʒ�������Ŀ�����̣�����ǰҳû���ҵ���������0-2�����̣�������ҳ�ٲ���Ŀ�����̣�����ѭ��
 *  ���ҵ�Ŀ�����̲������ɺ���������2-8������
 */
function doTapForScanGoodsByTitle(parentCN, className, titleStr, page, size) {
    var frontSize = 0;
    var backSize = 0;
    var count = 0;

    if (1 == page) {
        frontSize = 0;
        backSize = size;
    } else {
        frontSize = size - 25;
        backSize = size;
    }

    for (var i = frontSize; i < backSize; i++) {
        var titelTv = document.getElementsByClassName(className)[i];
        if (null == titelTv) {
            localMethod.JI_showToast("����Խ�磡��");
            return;
        }
        titelTxt = titelTv.innerHTML;

        if (!isTargetSearched && titelTxt.indexOf(titleStr) >= 0) {
            isTargetSearched = true;

            // detailWebViewִ����Ʒ����ҳ���������
            var url = getUrlFromHref(parentCN, i);
            localMethod.JI_doEnterShop(url);
            //localMethod.JI_createLog("Page" + page + "####" + titelTxt);

            // listWebViewִ�з�ҳˢ����Ʒlist������
            localMethod.JI_doGetNextPage(20);
            return;
        } else {
            count++;
        }
    }

    // ���ҵ�Ŀ�����̲������ɺ�����������������
    if (isTargetSearched) {
        if (0 != restScanCount) {
            restScanCount--;
            localMethod.JI_showToast(restScanCount);
        } else {
            return;
        }
    }

    // ��ǰҳ�����������������ʱ������߼�
    if (backSize - frontSize == count) {

        var randomTime = getRandomNum(1, 20);
        var randomIndex1 = getRandomNum(frontSize, backSize);
        var randomIndex2 = getRandomNum(frontSize, backSize);
        var rangeNum = Math.abs(randomIndex2 - randomIndex1);

        //localMethod.JI_showToast(rangeNum);
        var url = getUrlFromHref(parentCN, randomIndex2);
        if (rangeNum < 10) {
            localMethod.JI_doEnterShop(url);
            //localMethod.JI_createLog("Page" + page + "####" + url);
        } else if (rangeNum > 17) {
            localMethod.JI_doEnterShop(url);
            //localMethod.JI_createLog("Page" + page + "####" + url);
            localMethod.JI_doScanCurrentPage(randomTime);
            return;
        }
    }

    localMethod.JI_doGetNextPage(randomTime);

}

///** */ 
//function doTapForScanOtherGoods(parentCN, randomIndex) {
//    //localMethod.JI_showToast(index);
//    $("." + parentCN).eq(randomIndex).children(".p").children("a").trigger("tap");
//    localMethod.JI_doCloseAlert();
//    localMethod.JI_doScanOtherGoods();
//}

/** �õ�ָ��index��URL*/
function getUrlFromHref(parentCN, index) {
    return $("." + parentCN).eq(index).children(".p").children("a").attr("href");
}

/** �õ�ָ��TAG��������������ȡlist������*/
function getSize(className) {
    var text = document.getElementsByClassName(className).length;
    return text;
}

/** �õ�ָ����Χ�������*/
function getRandomNum(Min,Max)
{   
   var Range = Max - Min;   
   var Rand = Math.random();   
   return(Min + Math.round(Rand * Range));   
}   


