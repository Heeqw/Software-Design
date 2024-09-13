package refactoring;

/**
 * Created by Administrator on 2018/4/1.
 */

import java.util.*;

public class TXZ { // 推箱子游戏
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random ra = new Random();
        System.out.println("玩家：♀\n箱子：★\n目的地：⊙");
        System.out.println("操作：w-上,s-下,a-左,d-右,Set-设置,Look-查看,bug-扭转乾坤,Esc-结束\n\n");
        int gqxs = 0, guanqia = 3; // 关卡设置
        int ndsz = 0, ndxs = 4;// 难度系数设置
        int jiabug = 0; // bug增加变量
        int ck = 0;// 窗口丶分辨率设置变量
        over: for (int gg = 0, bug = 0, nd = 40; gg < guanqia + gqxs; gg++, nd += (ndxs + ndsz)) {// 创建关卡
            int maxbug = 3 + jiabug; // bug最大初始值设置
            System.out.println(" 推箱子第" + (gg + 1) + "关！");
            int zdi = ra.nextInt(10 + ck);
            int zdj = ra.nextInt(10 + ck);// 终点的创建
            int ri = ra.nextInt(10 + ck), rj = ra.nextInt(10 + ck); // 人物创建出生点
            int xi = ra.nextInt(8 + ck - 2) + 1, xj = ra.nextInt(8 + ck - 2) + 1; // 箱子创建出生点
            int xii = 0, xjj = 0, rii = 0, rjj = 0, zdii = 0, zdjj = 0;
            xii = xi;
            xjj = xj;
            rii = ri;
            rjj = rj;
            zdii = zdi;
            zdjj = zdj;
            int q[] = new int[nd + 40];// 墙丶障碍物的创建+20个防随机冲突点
            for (int i = 0; i < q.length; i++) {
                q[i] = ra.nextInt(10 + ck); // 随机定义障碍物
            }
            // 人物的创建
            boolean txz = true;
            // ---------------------------- true ------------------测试第一遍输出

            int ma[][] = new int[10 + ck][10 + ck];
            ma[ri][rj] = 1;
            // 箱子的创建
            ma[xi][xj] = 2;
            ma[zdi][zdj] = 3;
            int qq[] = new int[nd];// 用来存放没有冲突的障碍物位置坐标数组
            for (int i = 0, ii = 0; i < q.length - 1; i += 2) { // 判断随机障碍物是否与人丶箱子丶终点出生点相冲突
                if (ri == q[i] && rj == q[i + 1] || xi == q[i] && xj == q[i + 1] || zdi == q[i] && zdj == q[i + 1]) {
                    continue;
                } else {
                    ma[q[i]][q[i + 1]] = 4;// 障碍物赋值
                    qq[ii] = q[i];
                    qq[ii + 1] = q[i + 1];// 无冲突障碍物赋值到qq[]
                    ii += 2;
                }
                if (ii == nd) {
                    break;
                }
            }
            for (int i = 0; i < ma.length; i++) {
                for (int j = 0; j < ma[1].length; j++) {
                    if (ma[i][j] == 0) {
                        System.out.print("□");// 空
                    } else if (ma[i][j] == 1) {
                        System.out.print("♀");// 人
                    } else if (ma[i][j] == 2) {
                        System.out.print("★");// 地
                    } else if (ma[i][j] == 3) {
                        System.out.print("⊙");// 终点
                    } else if (ma[i][j] == 4) {
                        System.out.print("■");// 障碍物
                    }
                }
                System.out.println();
            }
            // ----------------------------- true --------------测试第一遍输出
            while (txz) {
                // 地图的创建
                int map[][] = new int[10 + ck][10 + ck];
                // -----------人物的移动---------true-----人物的移动丶箱子移动丶障碍物判断
                String move = sc.next();
                if (move.equals("w")) {
                    ri--;
                    if (ri < 0) {
                        ri = 0;// 判断是否越界
                    }
                    if (ri == xi && rj == xj) {// 箱子移动
                        xi--;
                    }
                    for (int i = 0; i < qq.length - 1; i += 2) {
                        if (ri == qq[i] && rj == qq[i + 1]) {// -------障碍物判断
                            ri++;
                        }
                        if (xi == qq[i] && xj == qq[i + 1]) {
                            xi++;
                            ri++;
                        } // true
                    }
                    if (xi < 0) {
                        xi = 0;
                    }

                } else if (move.equals("a")) {
                    rj--;
                    if (rj < 0) {
                        rj = 0;
                    }
                    if (ri == xi && rj == xj) {
                        xj--;
                    }
                    for (int i = 0; i < qq.length - 1; i += 2) {
                        if (ri == qq[i] && rj == qq[i + 1]) {
                            rj++;
                        }
                        if (xi == qq[i] && xj == qq[i + 1]) {
                            xj++;
                            rj++;
                        }
                    }
                    if (xj < 0) {
                        xj = 0;
                    }
                } else if (move.equals("d")) {
                    rj++;
                    if (rj > map[1].length - 1) {
                        rj = map[1].length - 1;
                    }
                    if (ri == xi && rj == xj) {
                        xj++;
                    }
                    for (int i = 0; i < qq.length - 1; i += 2) {
                        if (ri == qq[i] && rj == qq[i + 1]) {
                            rj--;
                        }
                        if (xi == qq[i] && xj == qq[i + 1]) {
                            xj--;
                            rj--;
                        }
                    }
                    if (xj > map[1].length - 1) {
                        xj = map[1].length - 1;
                    }
                } else if (move.equals("s")) {
                    ri++;
                    if (ri > map.length - 1) {
                        ri = map.length - 1;
                    }
                    if (ri == xi && rj == xj) {
                        xi++;
                    }
                    for (int i = 0; i < qq.length - 1; i += 2) {
                        if (ri == qq[i] && rj == qq[i + 1]) {
                            ri--;
                        }
                        if (xi == qq[i] && xj == qq[i + 1]) {
                            xi--;
                            ri--;
                        }
                    }
                    if (xi > map.length - 1) {
                        xi = map.length - 1;
                    }
                } else if (move.equals("Esc")) {
                    break over;
                } else if (move.equals("bug")) {
                    gg--;
                    bug++;
                    if (maxbug - bug == 1) {
                        System.out.println("注意！可用bug数量还有:1个\n");
                    }
                    nd -= (ndxs + ndsz);
                    if (bug > maxbug) {
                        System.out.println("\n\n\n 你耍我吗？玩个这个游戏还");
                        System.out.print("要作弊这么多次....you→ rubbish ↓\n\n");
                        break over;
                    }
                    continue over; // ---如果程序错误就跳过重来！
                } else if (move.equals("Set")) {// true
                    System.out.println("最大关卡设置：(输入序号)"); // 游戏的设置选项
                    System.out.println("1.四级");
                    System.out.println("2.六级");
                    System.out.println("3.八级");
                    String sg = sc.next();
                    int sndsz = 0;
                    sndsz = ndsz;
                    if (sg.equals("1")) {
                        gqxs = 1;
                    } else if (sg.equals("2")) {
                        gqxs = 3;
                        jiabug += 2;
                    } else if (sg.equals("3")) {
                        gqxs = 5;
                        jiabug += 3;
                    }
                    System.out.println("难度系数设置：(输入序号)");
                    System.out.println("1.简单");
                    System.out.println("2.一般");
                    System.out.println("3.困难");
                    String sr = sc.next();
                    if (sr.equals("1")) {
                        ndsz = 0;
                    } else if (sr.equals("2")) {
                        ndsz = 2;
                        jiabug++;
                    } else if (sr.equals("3")) {
                        ndsz = 4;
                        jiabug += 2;
                    }
                    System.out.println("游戏窗口设置：(输入序号)");
                    System.out.println("1. 10×10分辨率");
                    System.out.println("2. 15×15分辨率");
                    System.out.println("3. 20×20分辨率");
                    String sck = sc.next();
                    if (sck.equals("1")) {
                        ck = 0;
                        gg--;
                        nd -= 4;
                        continue over;
                    } else if (sck.equals("2")) {
                        ck = 5;
                        gg--;
                        nd -= 4;
                        nd = (int) (40 + 40 * 2.5);
                        ndxs = (int) (4 + 4 * 2.5);
                        ndsz = sndsz + sndsz * 2;
                        continue over;
                    } else if (sck.equals("3")) {
                        ck = 10;
                        gg--;
                        nd -= 4;
                        nd = 40 + 40 * 4;
                        ndxs = 4 + 4 * 4;
                        ndsz = sndsz + sndsz * 3;
                        continue over;
                    } // --------------------------------------------------------
                } else if (move.equals("Look")) {
                    System.out.println("系数查看....");
                    System.out.println("当前关卡： " + (gg + 1));
                    System.out.println("最大关卡数： " + (guanqia + gqxs));
                    System.out.println("当前可用bug：" + (maxbug - bug));
                    System.out.println("当前难度系数： " + ndsz);
                    System.out.println("当前障碍物： " + (nd / 2 - 2) + "个");
                    System.out.println("每关增长障碍物： " + (ndxs + ndsz) / 2 + "个");
                    System.out.println("当前分辨率为： " + (10 + ck) + "×" + (10 + ck));
                }
                String tuixiangzi = "";
                if (xi == zdi && xj == zdj) {
                    txz = false;
                    tuixiangzi = "胜"; // 胜利的条件
                }
                if (txz) {
                    for (int i = 0, ii = 0; i < q.length - 1; i += 2) { // 判断随机障碍物是否与人丶箱子丶终点出生点相冲突
                        if (rii == q[i] && rjj == q[i + 1] || xii == q[i] && xjj == q[i + 1]
                                || zdii == q[i] && zdjj == q[i + 1]) {
                            continue;
                        } else {
                            map[q[i]][q[i + 1]] = 4;// 障碍物赋值
                            ii++;
                        }
                        if (ii == nd / 2) {
                            break;
                        }
                    }
                    map[ri][rj] = 1;
                    // 目的地的创建
                    map[xi][xj] = 2;
                    map[zdi][zdj] = 3;
                    // 遍历二维数组
                    for (int i = 0; i < map.length; i++) {
                        for (int j = 0; j < map[1].length; j++) {
                            if (map[i][j] == 0) {
                                System.out.print("□");// 空
                            } else if (map[i][j] == 1) {
                                System.out.print("♀");// 人
                            } else if (map[i][j] == 2) {
                                System.out.print("★");// 地
                            } else if (map[i][j] == 3) {
                                System.out.print("⊙");// 目的地
                            } else if (map[i][j] == 4) {
                                System.out.print("■");// 墙
                            }
                        }
                        System.out.println();
                    }
                }
                if (tuixiangzi.equals("胜")) {
                    System.out.println(" 　 === ===　 　 ");
                    System.out.println("　　　　　||　 ||　 ");
                    System.out.println("　　　　　|| || ");
                    System.out.println("　　　　　.-'^'-. ");
                    System.out.println("　　　　.' a___a `. ");
                    if (gg == (guanqia + gqxs - 1)) {
                        System.out.println("　　　　'. ._I_. .' 恭喜你过通关！ You are the best！");
                    } else {
                        System.out.println("　　　　'. ._I_. .' 恭喜通过第" + (gg + 1) + "关！ ");
                    }
                    System.out.println("　　____{.`----- .}____ ");
                    System.out.println("　 [###(__)#### \n\n");

                }
            }
        }
    }
}
