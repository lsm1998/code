/*
 * 作者：刘时明
 * 时间：2020/5/2-0:48
 * 作用：
 */
package com.lsm1998.structure.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CityGraph
{
    // 城市集合
    private List<String> cityList;
    // 邻接矩阵
    private int[][] edges;
    // 边的数量
    private int numOfEdges;
    // 记录某个节点是否被访问
    private boolean[] visitedArray;

    public CityGraph(int size)
    {
        edges = new int[size][size];
        cityList = new ArrayList<>(size);
        visitedArray = new boolean[size];
        numOfEdges = 0;
    }

    // 获取第一个邻接节点的下标w
    private int getFirstNeighbor(int index)
    {
        for (int i = 0; i < cityList.size(); i++)
        {
            if (edges[index][i] > 0)
            {
                return i;
            }
        }
        return -1;
    }

    // 根据前一个邻接节点的下标获取下一个邻接节点
    private int getNextNeighbor(int v1, int v2)
    {
        for (int i = v2 + 1; i < cityList.size(); i++)
        {
            if (edges[v1][i] > 0)
            {
                return i;
            }
        }
        return -1;
    }

    private void dfs(boolean[] visitedArray, int i)
    {
        System.out.print(getCity(i) + "->");
        // 节点设置为已访问
        visitedArray[i] = true;
        // 获取第一个邻接节点
        int w = getFirstNeighbor(i);
        while (w != -1)
        {
            if (!visitedArray[w])
            {
                dfs(visitedArray, w);
            }
            w = getNextNeighbor(i, w);
        }
    }
    
    /**
     * 深度优先
     */
    public void dfs()
    {
        for (int i = 0; i < cityList.size(); i++)
        {
            if (!visitedArray[i])
            {
                dfs(visitedArray, i);
            }
        }
    }

    // 对一个节点广度优先遍历
    private void bfs(boolean[] visitedArray, int i)
    {
        // 队列头节点
        int u;
        // 邻接节点w
        int w;
        // 队列，记录节点访问顺序
        LinkedList<Integer> queue = new LinkedList<>();
        // 访问节点
        System.out.print(getCity(i) + "->");
        // 节点设置为已访问
        visitedArray[i] = true;
        // 将节点加入队列
        queue.addLast(i);
        while (!queue.isEmpty())
        {
            u = queue.removeFirst();
            w = getFirstNeighbor(u);
            while (w != -1)
            {
                if (!visitedArray[w])
                {
                    System.out.print(getCity(w) + "->");
                    visitedArray[w] = true;
                    queue.addLast(w);
                }
                w = getNextNeighbor(u, w);
            }
        }
    }

    /**
     * 广度优先
     */
    public void bfs()
    {
        for (int i = 0; i < cityList.size(); i++)
        {
            if (!visitedArray[i])
            {
                bfs(visitedArray, i);
            }
        }
    }

    public void insertCity(String city)
    {
        cityList.add(city);
    }

    public void insertEdge(int v1, int v2, int distance)
    {
        edges[v1][v2] = distance;
        edges[v2][v1] = distance;
        numOfEdges++;
    }

    public int getCityNum()
    {
        return cityList.size();
    }

    public int getNumOfEdges()
    {
        return numOfEdges;
    }

    public String getCity(int index)
    {
        return cityList.get(index);
    }

    public int getDistance(int v1, int v2)
    {
        return edges[v1][v2];
    }

    public int getDistanceByCity(String city1, String city2)
    {
        int v1 = this.cityList.indexOf(city1);
        int v2 = this.cityList.indexOf(city2);
        if (v1 == -1 || v2 == -1)
        {
            return -1;
        } else
        {
            return getDistance(v1, v2);
        }
    }

    public void showGraph()
    {
        for (int[] like : edges)
        {
            System.out.println(Arrays.toString(like));
        }
    }
}
